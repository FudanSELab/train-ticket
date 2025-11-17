from __future__ import annotations

import json
import os
import urllib.request

from pydantic import ValidationError

from openapi_server.models.voucher_dto import VoucherDto
from openapi_server.models.voucher_request import VoucherRequest
from openapi_server.impl.client import get_session
from openapi_server.impl import dao


class VoucherService:
    def __init__(self) -> None:
        dao.create_schema()

    def _query_order_by_id(self, order_id: str) -> dict:
        """Call order-service to retrieve order details as dict."""
        order_url = os.getenv("ORDER_SERVICE_URL", "http://ts-order-service:12031")
        url = f"{order_url}/api/v1/order/order/{order_id}"
        headers = {"User-Agent": "voucher-service", "Content-Type": "application/json"}
        req = urllib.request.Request(url=url, headers=headers)
        with urllib.request.urlopen(req) as resp:
            payload = resp.read()
            return json.loads(payload)

    def _model_to_dto(self, model: dao.Voucher) -> VoucherDto:
        return VoucherDto.from_dict(model.to_dict())

    def get_or_create_voucher(self, voucher_request: VoucherRequest) -> VoucherDto:
        """Return existing voucher or create a new one from order-service data."""
        order_id = voucher_request.order_id

        with get_session() as session:
            voucher = dao.get_voucher_by_order_id(session, order_id)
            if voucher:
                return self._model_to_dto(voucher)

            # Fetch order details and create voucher
            order_resp = self._query_order_by_id(order_id)
            if order_resp.get("status", 200) != 200:
                raise ValueError("Failed to fetch order data")
            order = order_resp["data"]

            try:
                voucher_model = dao.insert_voucher(session, order)
                return self._model_to_dto(voucher_model)
            except ValidationError as ve:
                raise ValueError(str(ve))

voucher_service = VoucherService()

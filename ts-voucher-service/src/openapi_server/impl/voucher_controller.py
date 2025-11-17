from __future__ import annotations

from fastapi import HTTPException, status

from openapi_server.apis.default_api_base import BaseDefaultApi
from openapi_server.impl.voucher_service import voucher_service
from openapi_server.models.voucher_dto import VoucherDto
from openapi_server.models.voucher_request import VoucherRequest

import logging

logger = logging.getLogger(__name__)
logging.basicConfig(
    level=logging.INFO,  # 关键：设置日志级别为 INFO
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'  # 可选：自定义输出格式
)

class VoucherController(BaseDefaultApi):
    async def api_v1_vouchers_post(self, voucher_request: VoucherRequest) -> VoucherDto:  # noqa: D401
        """Handle POST /api/v1/vouchers"""
        try:
            logger.info(f"Received voucher request: {voucher_request}")
            return voucher_service.get_or_create_voucher(voucher_request)
        except ValueError as e:
            raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))

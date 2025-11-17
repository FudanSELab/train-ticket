# coding: utf-8

from fastapi.testclient import TestClient


from typing import Any  # noqa: F401
from openapi_server.models.voucher_dto import VoucherDto  # noqa: F401
from openapi_server.models.voucher_request import VoucherRequest  # noqa: F401


def test_api_v1_vouchers_post(client: TestClient):
    """Test case for api_v1_vouchers_post

    Generate a voucher if not exist for a train ticket order and return the voucher information
    """
    voucher_request = {"order_id":"orderId"}

    headers = {
    }
    # uncomment below to make a request
    #response = client.request(
    #    "POST",
    #    "/api/v1/vouchers",
    #    headers=headers,
    #    json=voucher_request,
    #)

    # uncomment below to assert the status code of the HTTP response
    #assert response.status_code == 200


# coding: utf-8

from typing import Dict, List  # noqa: F401
import importlib
import pkgutil

from openapi_server.apis.default_api_base import BaseDefaultApi
import openapi_server.impl

from fastapi import (  # noqa: F401
    APIRouter,
    Body,
    Cookie,
    Depends,
    Form,
    Header,
    HTTPException,
    Path,
    Query,
    Response,
    Security,
    status,
)

from openapi_server.models.extra_models import TokenModel  # noqa: F401
from typing import Any
from openapi_server.models.voucher_dto import VoucherDto
from openapi_server.models.voucher_request import VoucherRequest


router = APIRouter()

ns_pkg = openapi_server.impl
for _, name, _ in pkgutil.iter_modules(ns_pkg.__path__, ns_pkg.__name__ + "."):
    importlib.import_module(name)


@router.post(
    "/api/v1/vouchers",
    responses={
        200: {"model": VoucherDto, "description": "Successful voucher retrieval"},
        400: {"description": "Invalid request payload"},
        500: {"description": "Internal server error"},
    },
    tags=["default"],
    summary="Generate a voucher if not exist for a train ticket order and return the voucher information",
    response_model_by_alias=True,
)
async def api_v1_vouchers_post(
    voucher_request: VoucherRequest = Body(None, description=""),
) -> VoucherDto:
    if not BaseDefaultApi.subclasses:
        raise HTTPException(status_code=500, detail="Not implemented")
    return await BaseDefaultApi.subclasses[0]().api_v1_vouchers_post(voucher_request)

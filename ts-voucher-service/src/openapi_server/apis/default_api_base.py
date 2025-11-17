# coding: utf-8

from typing import ClassVar, Dict, List, Tuple  # noqa: F401

from typing import Any
from openapi_server.models.voucher_dto import VoucherDto
from openapi_server.models.voucher_request import VoucherRequest


class BaseDefaultApi:
    subclasses: ClassVar[Tuple] = ()

    def __init_subclass__(cls, **kwargs):
        super().__init_subclass__(**kwargs)
        BaseDefaultApi.subclasses = BaseDefaultApi.subclasses + (cls,)
    async def api_v1_vouchers_post(
        self,
        voucher_request: VoucherRequest,
    ) -> VoucherDto:
        ...

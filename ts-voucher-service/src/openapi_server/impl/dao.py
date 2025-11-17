from __future__ import annotations

from datetime import date
from typing import Optional, Dict, Any

from sqlalchemy import Column, Integer, String, Float, Date
from sqlalchemy.orm import Session

from openapi_server.impl.client import Base, engine

class Voucher(Base):
    __tablename__ = "voucher"

    voucher_id = Column(Integer, primary_key=True, autoincrement=True)
    order_id = Column(String(1024), nullable=False)
    travelDate = Column(String(10), nullable=False)
    travelTime = Column(String(8), nullable=False)
    contactName = Column(String(1024), nullable=False)
    trainNumber = Column(String(1024), nullable=False)
    seatClass = Column(Integer, nullable=False)
    seatNumber = Column(String(1024), nullable=False)
    startStation = Column(String(1024), nullable=False)
    destStation = Column(String(1024), nullable=False)
    price = Column(Float, nullable=False)

    # Convenience to convert to dict matching VoucherDto aliases
    def to_dict(self) -> Dict[str, Any]:
        return {
            "voucherId": self.voucher_id,
            "orderId": self.order_id,
            "travelDate": date.fromisoformat(self.travelDate),
            "travelTime": self.travelTime,
            "contactName": self.contactName,
            "trainNumber": self.trainNumber,
            "seatClass": self.seatClass,
            "seatNumber": self.seatNumber,
            "startStation": self.startStation,
            "destStation": self.destStation,
            "price": self.price,
        }


def create_schema() -> None:
    """Ensure voucher table exists."""
    Base.metadata.create_all(bind=engine)


def get_voucher_by_order_id(session: Session, order_id: str) -> Optional[Voucher]:
    return session.query(Voucher).filter_by(order_id=order_id).first()


def insert_voucher(session: Session, order: dict) -> Voucher:
    voucher = Voucher(
        order_id=order["id"],
        travelDate=order["travelDate"],
        travelTime=order["travelTime"],
        contactName=order["contactsName"],
        trainNumber=order["trainNumber"],
        seatClass=order["seatClass"],
        seatNumber=order["seatNumber"],
        startStation=order["from"],
        destStation=order["to"],
        price=order["price"],
    )
    session.add(voucher)
    session.commit()
    session.refresh(voucher)
    return voucher




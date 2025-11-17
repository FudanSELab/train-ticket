FROM python:3.7 AS builder

WORKDIR /usr/src/app

RUN python3 -m venv /venv
ENV PATH="/venv/bin:$PATH"

RUN pip install --upgrade pip

COPY . .
RUN pip install --no-cache-dir .


FROM python:3.7 AS test_runner
WORKDIR /tmp
COPY --from=builder /venv /venv
COPY --from=builder /usr/src/app/tests tests
ENV PATH=/venv/bin:$PATH

# install test dependencies
RUN pip install pytest

# run tests
RUN pytest tests


FROM python:3.7 AS service
WORKDIR /app

COPY --from=test_runner /venv /venv

ENV PATH=/venv/bin:$PATH \
    PYTHONUNBUFFERED=1

EXPOSE 8080

CMD ["uvicorn", "openapi_server.main:app", "--host", "0.0.0.0", "--port", "8080"]

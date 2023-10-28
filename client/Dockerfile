FROM python:latest

COPY . /app

WORKDIR /app

RUN apt-get update
RUN pip install thrift

ENTRYPOINT ["python", "client.py"]
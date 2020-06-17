FROM python:3

WORKDIR /usr/src/app


RUN pip install --no-cache-dir mysql-connector-python

COPY ./uploadData.py .
COPY ./popola_db.txt .
CMD [ "python", "./uploadData.py" ]
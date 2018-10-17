import boto3
import requests
from requests_aws4auth import AWS4Auth
from elasticsearch import Elasticsearch, RequestsHttpConnection

host = 'search-mining2-2jysqqy7hjm2to2uvpyutlfqze.us-east-1.es.amazonaws.com'
region = 'us-east-1' # e.g. us-west-1
service = 'es'

credentials = boto3.Session().get_credentials()
awsauth = AWS4Auth(credentials.access_key, credentials.secret_key, region, service, session_token=credentials.token)

es = Elasticsearch(
    hosts = [{'host': host, 'port': 443}],
    http_auth = awsauth,
    use_ssl = True,
    verify_certs = True,
    connection_class = RequestsHttpConnection
)

headers = { "Content-Type": "application/json" }

s3 = boto3.client('s3')


# Lambda execution starts here
def handler(event, context):

    id = 0
    for record in event['Records']:

        # Get the bucket name and key for the new file
        bucket = record['s3']['bucket']['name']
        key = record['s3']['object']['key']

        # Get, read, and split the file into lines
        obj = s3.get_object(Bucket=bucket, Key=key)
        body = obj['Body'].read()
        lines = body.splitlines()
        
        # Match the regular expressions to each line and index the JSON
        for line in lines:
            es.index(index="lambda-s3-index", doc_type="_doc", id=id, body=line)
            id = id + 1
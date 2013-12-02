from flask import Response
import requests
from json import loads
import uuid

config_file = open('config.json', 'r')
config = loads(config_file.read())

url_map = config['url_maps'];

def toJSON(str):
	res = Response(str, content_type='application/json')
	return res


def get_service(url_key, params=''):
	if url_key not in url_map:
		return None

	cookies = dict(ppk='id')

	r = requests.get(url_map[url_key] + params, cookies=cookies)
	return r.text

def post_service(url_key, params='', post_data={}):
    if url_key not in url_map:
        return None
    service_url = url_map[url_key] + params

    r = requests.post(service_url, data=post_data)
    return r.text


def gen_id():
    return uuid.uuid4().hex
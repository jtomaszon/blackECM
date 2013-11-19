from flask import Response
import requests
from json import loads

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
from flask import Response
import requests

url_map = {
	'/collab/post/': 'http://foolabs.us/collab/post/'
}

def toJSON(str):
	return Response(str, content_type='application/json')


def get_service(url_key, params=''):
	if url_key not in url_map:
		return None

	r = requests.get(url_map[url_key] + params)
	return r.text
from flask import Response

def toJSON(str):
	return Response(str, content_type='application/json')

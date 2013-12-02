#from flask import Flask, redirect, url_for, Response
#from json import loads
import routes

routes.app.secret_key = '~\x19{8\xb9*\x96\xd2h\xe3\x0c\xd7\x9d\xf5\xd5\x80\xf1\xe2+J\xc9\xd5\xb9\x9a'

if __name__ == '__main__':
	routes.app.run(debug=True)
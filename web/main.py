#from flask import Flask, redirect, url_for, Response
#from json import loads
import routes


'''
app = Flask(__name__)


@app.route('/user')
def user():
	return Response("{'id': 1, 'name': 'Willian', 'email': 'o.chambs@gmail.com'}", content_type='application/json')

@app.route('/fixed')
def fixed():
	the_url = url_for('user')
	return redirect(the_url)
'''

if __name__ == '__main__':
	routes.app.run(debug=True)
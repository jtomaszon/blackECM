from flask import Flask, render_template, request, Response, session
import util

app = Flask('__main__')

@app.route('/')
def index():
	
	if not 'uuid' in session:
		session['uuid'] = util.gen_id()
	else:
		print('we have session uuid: %s' % session['uuid'])

	return render_template('index.html')

@app.route('/loginform')
def loginform():
	return render_template('loginform.html')

@app.route('/welcome')
def welcome():
	return render_template('welcome.html')

@app.route('/documents')
def documents():
	return render_template('documents.html')

@app.route('/security')
def security():
	return render_template('security.html')

@app.route('/social')
def social():
	return render_template('social.html')

@app.route('/workflow')
def workflow():
	return render_template('workflow.html')


@app.route('/dologin', methods=['POST'])
def dologin():

	email = request.form['email']
	password = request.form['password']

	if email == 'adm@foo.com' and password == 'adm':
		return util.toJSON('{"success": true}')
	return util.toJSON('{"success": false}')


@app.route('/collab/post/<post_id>')
def post(post_id):
	res = util.get_service('/collab/post/', post_id)
	return util.toJSON(res)

@app.route('/security/session', methods=['GET'])
def security_get_session():
	res = util.get_service('/security/session/')
	return util.toJSON(res)

@app.route('/security/session', methods=['POST'])
def security_create_session():
	res = util.post_service('/security/session/', '', {'ppk': 'aaa'})
	return util.toJSON(res)


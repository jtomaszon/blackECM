from flask import Flask, render_template

app = Flask('__main__')

@app.route('/')
def index():
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


import subprocess
from flask import Flask, request, jsonify
import platform

app = Flask(__name__)

@app.route('/send_command', methods=['GET'])
def send_command():
    response = """Please Enter The Command "ping -n4 localhost" to check system status:
        <form method="post" action="/execute">
             <input id="command" type="text" class="searchField" name="command" placeholder="Enter Command: ">
             <input type="submit" class="submit" value="Submit"> 
        </form>
    """
    return response

@app.route('/execute', methods=['POST'])
def execute_command():
    command = request.form.get('command')
    if not command or command.strip() == '':
        return "Command parameter is missing or empty.", 400

    if 'localhost' not in command:
        return "Invalid command parameter.", 400

    try:
        process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        output, _ = process.communicate()
        exit_code = process.returncode

        if exit_code == 0:
            return output
        else:
            return "Command execution error.", 500
    except Exception:
        return "Failed to execute command.", 500

@app.route('/', methods=['GET'])
def welcome():
    response = "WELCOME!"

    return response

if __name__ == '__main__':
    app.run(host='localhost', port=8080)
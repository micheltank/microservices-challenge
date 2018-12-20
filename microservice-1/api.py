from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flaskext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'root'
app.config['MYSQL_DATABASE_DB'] = 'ms1'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'

mysql.init_app(app)

api = Api(app)


class AuthenticateUser(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('login', type=str, help='Login address for Authentication')
            parser.add_argument('password', type=str, help='Password for Authentication')
            args = parser.parse_args()

            _userLogin = args['login']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spAthenticateUser', (_userPassword,))
            data = cursor.fetchall()

            if (len(data) > 0):
                if (str(data[0][2]) == _userPassword):
                    return {'authenticate': True}

            return {'authenticate': False, 'message': 'Fail on authentication'}

        except Exception as e:
            return {'error': str(e)}


class GetAllUsers(Resource):
    def get(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('id', type=str)
            args = parser.parse_args()

            _userId = args['id']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spGetAllUsers')
            data = cursor.fetchall()

            items_list = [];
            for item in data:
                i = {
                    'id': item[0],
                    'login': item[1],
                    'password': item[2],
                    'cpf': item[3],
                    'name': item[4],
                    'address': item[5]
                }
                items_list.append(i)

            return items_list

        except Exception as e:
            return {'error': str(e)}


class CreateUser(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('login', type=str, help='Login to create user')
            parser.add_argument('password', type=str, help='Password to create user')
            parser.add_argument('cpf', type=str, help='Cpf to create user')
            parser.add_argument('name', type=str, help='Name to create user')
            parser.add_argument('address', type=str, help='Address to create user')
            args = parser.parse_args()

            _userLogin = args['login']
            _userPassword = args['password']
            _userCpf = args['cpf']
            _userName = args['name']
            _userAddress = args['address']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateUser', (_userLogin, _userPassword, _userCpf, _userName, _userAddress))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'Message': 'User creation success'}
            else:
                return {'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


class CreateDebt(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('user_id', type=str, help='User id to create debt')
            parser.add_argument('value', type=str, help='Value to create debt')
            parser.add_argument('date', type=str, help='Date to create debt')
            args = parser.parse_args()

            _debtUserId = args['user_id']
            _debtValue = args['value']
            _debtDate = args['date']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateDebt', (_debtUserId, _debtValue, _debtDate))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'Message': 'Debt creation success'}
            else:
                return {'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


api.add_resource(AuthenticateUser, '/AuthenticateUser')
api.add_resource(CreateUser, '/CreateUser')
api.add_resource(CreateDebt, '/CreateDebt')
api.add_resource(GetAllUsers, '/GetAllUsers')

if __name__ == '__main__':
    app.run(debug=True)
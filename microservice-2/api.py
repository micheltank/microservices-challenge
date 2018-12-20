from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flaskext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'root'
app.config['MYSQL_DATABASE_DB'] = 'ms2'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'

mysql.init_app(app)

api = Api(app)


class GetAllProfile(Resource):
    def get(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('id', type=str)
            args = parser.parse_args()

            _userId = args['id']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spGetAllProfiles')
            data = cursor.fetchall()

            items_list = [];
            for item in data:
                i = {
                    'id': item[0],
                    'birthday': item[1],
                    'address': item[2]
                }
                items_list.append(i)

            return items_list

        except Exception as e:
            return {'error': str(e)}


class CreatProfile(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('birthday', type=str)
            parser.add_argument('address', type=str)
            args = parser.parse_args()

            _userBirthday = args['birthday']
            _userAddress = args['address']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateProfile', (_userBirthday, _userAddress))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'Message': 'Profile creation success'}
            else:
                return {'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


class CreateIncome(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('profile_id', type=str)
            parser.add_argument('description', type=str)
            parser.add_argument('value', type=str)
            args = parser.parse_args()

            _debtProfileId = args['profile_id']
            _debtDescription = args['description']
            _debtValue = args['value']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateIncome', (_debtProfileId, _debtDescription, _debtValue))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'Message': 'Income creation success'}
            else:
                return {'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


class CreateProperty(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('profile_id', type=str)
            parser.add_argument('description', type=str)
            parser.add_argument('value', type=str)
            args = parser.parse_args()

            _debtProfileId = args['profile_id']
            _debtDescription = args['description']
            _debtValue = args['value']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateProperty', (_debtProfileId, _debtDescription, _debtValue))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'Message': 'Income creation success'}
            else:
                return {'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


api.add_resource(CreatProfile, '/CreatProfile')
api.add_resource(CreateIncome, '/CreateIncome')
api.add_resource(CreateProperty, '/CreateProperty')
api.add_resource(GetAllProfile, '/GetAllProfile')

if __name__ == '__main__':
    app.run(debug=True)
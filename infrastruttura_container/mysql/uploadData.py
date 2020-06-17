
import mysql.connector

#sudo pip3 install mysql-connector-python
try:
    mydb = mysql.connector.connect(
    host="mysql-server",
    user="root",
    password="root"
    )
    mycursor = mydb.cursor()
    mycursor.execute("CREATE DATABASE link_db")
except:
    print("errore creazione db")

mydb = mysql.connector.connect(
  host="mysql-server",
  user="root",
  password="root",
  database="link_db"
)
mycursor = mydb.cursor()
try:
    mycursor.execute("CREATE TABLE links (tipologia varchar(50), professore varchar(50), materia varchar(70), anno varchar(5), link VARCHAR(400) PRIMARY KEY);")
except:
    print("errore creazione tabella")

f= open("popola_db.txt","r")

lines=f.readlines()

for line in lines: 
    x=line.split(",")
    for link in x[4:]:
        sql= "INSERT INTO links " + \
                            "(tipologia, professore, materia, anno, link) " +  \
                        "VALUES ("+\
                        "'"+x[0]+"'," +\
                        "'"+x[1]+"',"+\
                        "'"+x[2]+"',"+\
                        "'"+x[3]+"',"+\
                        "'"+link+"');"
        try:
            mycursor.execute(sql)
        except:
            print("errore query")

mydb.commit()

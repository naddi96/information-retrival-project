import os
from tika import parser
'''raw=parser.from_file('2.ppt')
print(raw['content'].replace('\n',""))'''
from PyPDF2 import PdfFileWriter, PdfFileReader

def split_pdf(in_path,pdf_name,output_path):
    inputpdf = PdfFileReader(open(in_path+pdf_name, "rb"))
    for i in range(inputpdf.numPages):
        output = PdfFileWriter()
        output.addPage(inputpdf.getPage(i))
        with open(output_path+pdf_name+"_%s.pdf" % i, "wb") as outputStream:
            output.write(outputStream)



os.system('mkdir ../../corsi_pagine')
corsi=os.listdir('../../corsi')
for x in corsi:
    os.system('mkdir ../../corsi_pagine/'+x)
    pdfs=os.listdir('../../corsi/'+x)
    for pdf in pdfs:
        try:
            split_pdf('../../corsi/'+x+"/",pdf,'../../corsi_pagine/'+x+"/")
        except Exception as e:
            print(e)

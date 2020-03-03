import os
from tika import parser


def pdf_2_txt(input_path,pdf,output_path):
    f=open(output_path+pdf[:-3]+'txt',"w",encoding='utf-8')
    raw=parser.from_file(input_path+pdf)
    f.write(raw['content'])
    f.close()


output_path='../../corsi_pagine_txt/'
input_path='../../corsi_pagine/'
os.system('mkdir '+output_path)
corsi = os.listdir(input_path)



for x in corsi:
    os.system('mkdir ../../corsi_pagine_txt/'+x)
    pdfs=os.listdir('../../corsi_pagine/'+x)
    for pdf in pdfs:
        try:
            pdf_2_txt(input_path+x+'/',pdf,output_path+x+'/')        
        except Exception as e:
            print(e)
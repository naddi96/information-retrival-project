import os
from tika import parser
from nltk.tokenize import TweetTokenizer
tknzr = TweetTokenizer(strip_handles=True, reduce_len=True)

def pdf_2_txt(input_path,pdf,output_path):
    f=open(output_path+pdf[:-3]+'txt',"w",encoding='utf-8')
    raw=parser.from_file(input_path+pdf)
    x=raw['content'].replace('\n',' ')
    f.write(x)
   # f.write(' '.join(tknzr.tokenize(raw['content'])))
    f.close()
    return x

output_path='../../corsi_pagine_txt/'
input_path='../../corsi_pagine/'
os.system('mkdir '+output_path)
corsi = os.listdir(input_path)


dict={}
for corso in corsi:
    os.system('mkdir ../../corsi_pagine_txt/'+corso)
    pdfs=os.listdir('../../corsi_pagine/'+corso)
    dict[corso]={}
    for pdf in pdfs:
        try:
            stri=pdf_2_txt(input_path+corso+'/',pdf,output_path+corso+'/')        
            try:
                dict[corso][pdf[:pdf.rindex('_')]]=dict[corso][pdf[:pdf.rindex('_')]]+len(tknzr.tokenize(stri))
            except:
                dict[corso][pdf[:pdf.rindex('_')]]=len(tknzr.tokenize(stri))
        except Exception as e:
            print(e)

import json
with open('result.json', 'w') as fp:
    json.dump(dict, fp)
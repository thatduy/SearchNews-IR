import requests
from bs4 import BeautifulSoup
import codecs
import os
import glob
import threading


list_url =['https://thanhnien.vn/thoi-su/trang-%s.html', 
'https://thanhnien.vn/the-gioi/trang-%s.html',
'https://thanhnien.vn/chinh-tri/trang-%s.html',
'https://thanhnien.vn/tai-chinh-kinh-doanh/trang-%s.html',
'https://thanhnien.vn/doi-song/trang-%s.html',
'https://thanhnien.vn/van-hoa/trang-%s.html',
'https://thanhnien.vn/gioi-tre/trang-%s.html',
'https://thanhnien.vn/giao-duc/trang-%s.html',
'https://thanhnien.vn/suc-khoe/trang-%s.html',
'https://thanhnien.vn/cong-nghe/trang-%s.html']



if not os.path.exists('./dataset/'):
    os.makedirs('./dataset/')
def getnews(listlink):  #lấy ra nội dung bài báo 
    for link in listlink:
        req = requests.get(link)
        fname = link.rsplit('-', 1)[1].rsplit('.',1)[0]    #tách ra phần id để đặt tên file
        f= codecs.open("./dataset/thanhnien-%s.txt" %fname, "w", "utf-8")
        soup = BeautifulSoup(req.text, "lxml")
        title = soup.find(class_= 'details__headline')
        if title is not None:
            f.write(title.get_text() + os.linesep)
        listnew = soup.find(id='abody')
        if listnew is not None:
            for i in listnew.select('div'):
                if i != i.find('span'):
                    f.write(i.get_text() + os.linesep)         #lưu nội dung báo 
            for i in listnew.select('p'):
                if i != i.find('span'):
                    f.write(i.get_text() + os.linesep)         #lưu nội dung báo 
            f.close()
def getlinknews():              #lấy link của các bài báo 
    print('getting link..')
    f1= codecs.open("result_bds.txt", "w", "utf-8")
    list_link = []
    check = []
    for url in list_url:        #duyệt qua các mục tin thời sự, thế giới, thời trang...
        for j in range (1,150):  #duyệt qua 10 trang mỗi mục 
            req = requests.get(url%j)
            soup = BeautifulSoup(req.text, "lxml")
            listlink = soup.find_all('article')     #các nhãn chứa link dẫn đến bài báo 
            for i in listlink:
                if (i.find('a') is not None):
                    if (i.find('a').get('href')[0] == '/'):    #kiểm tra nếu bài báo đó ko phải là báo video, báo chữ sẽ có dạng /thoi-su/...
                        if 'https://thanhnien.vn/%s'%i.find('a').get('href') not in check:
                            f1.write(i.find('a').get('href') + os.linesep)
                            check.append('https://thanhnien.vn/%s'%i.find('a').get('href'))
                            list_link.append('https://thanhnien.vn/%s'%i.find('a').get('href'))
                            if len(list_link) == 50:
                                t = threading.Thread(target=getnews, args=(list_link,))
                                t.start()
                                list_link = []
    f1.close()
    print('get link done')


getlinknews()

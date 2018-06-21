
import requests
from bs4 import BeautifulSoup
import codecs
import os
import threading


def saveNews(listLink): #Hàm lưu nội dung bài báo
    for link in listLink: # Với mỗi link trong danh sách listLink
        req = requests.get(link)
        soup = BeautifulSoup(req.text, "lxml")
        k=link.split("-")
        id=k[-1].strip(".html")
        id1=id.split("/")
        id=id1[0]
        if not os.path.exists('./data/'): #Tạo folder chứa dữ liệu nếu chưa có
            os.makedirs('./data')
        f= codecs.open("./data/%s.txt"%id, "w", "utf-8")# Tạo file có tên tương ứng id để chứa nọi dung bài báo
        
        for part in soup.find_all("p", "Normal"): #Tìm các phần chứa nội dung bài báo
            t = part.string
            if t is not None:
                f.write(t  +  os.linesep)
        
        for part in soup.find_all("p", "Image"): #Tìm các phần chứa nội dung bài báo
            t = part.string
            if t is not None:
                f.write(t  +  os.linesep)
                
                    
        f.close()
    
listMenu =['https://vnexpress.net/tin-tuc/thoi-su/page/%s.html',
'https://vnexpress.net/tin-tuc/the-gioi/page/%s.html',
'https://kinhdoanh.vnexpress.net/page/%s.html',
'https://thethao.vnexpress.net/page/%s.html',
'https://giaitri.vnexpress.net/page/%s.html',
'https://vnexpress.net/tin-tuc/phap-luat/page/%s.html',
'https://vnexpress.net/tin-tuc/giao-duc/page/%s.html',
'https://giadinh.vnexpress.net/page/%s.html',
'https://dulich.vnexpress.net/page/%s.html',
'https://vnexpress.net/tin-tuc/khoa-hoc/page/%s.html',
'https://sohoa.vnexpress.net/page/%s.html',
'https://vnexpress.net/tin-tuc/oto-xe-may/page/%s.html',
'https://vnexpress.net/tin-tuc/cong-dong/page/%s.html',
'https://vnexpress.net/tin-tuc/tam-su/page/%s.html',
'https://vnexpress.net/tin-tuc/cuoi/page/%s.html']



def getNews(): # Hàm lấy link cáo bài báo
    list_Link=[]
    count =0
    f1= codecs.open("./SaveLink.txt", "w", "utf-8")
    for link in listMenu: # Chạy vòng lặp mỗi link trong listMenu, mỗi link là 1 thể loại báo
        for i in range (51,400):    #Chạy từ trang 1 đến 10 của link     
            req = requests.get(link%i)
            soup = BeautifulSoup(req.text, "lxml")        
            for title in soup.find_all("h3", "title_news"): # tìm các phần chứa h3 có class="title_news"                
                    tit = title.find("a").get("href") #gán t bằng link bài báo
                    list_Link.append(tit) #Thêm link vào danh sách listLink
                    f1.write(tit  +  os.linesep)                    
                    count+=1
                    if len(list_Link) == 100:
                                t = threading.Thread(target=saveNews, args=(list_Link,))
                                t.start()
                                list_Link = []
        
    f1.close()
    print("Done")
    print(count)
            

getNews()



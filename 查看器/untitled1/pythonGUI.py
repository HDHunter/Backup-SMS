import tkinter
from tkinter import *
from  tkinter import ttk
from tkinter.ttk import Treeview

from pythonmysql import sqll

top = Tk()

top.geometry('1000x400+400+300')
top.resizable(False, False)
frame = Frame(top)
scrollBar = tkinter.Scrollbar(frame)
frame.place(x=0, y=0, width=1000, height=350)
scrollBar.pack(side=tkinter.RIGHT, fill=tkinter.Y)

#Treeview组件，6列，显示表头，带垂直滚动条

tree = Treeview(frame,
                columns=('c1', 'c2', 'c3',

                'c4', 'c5', 'c6'),

                 show="headings",

                 yscrollcommand=scrollBar.set)

#设置每列宽度和对齐方式


tree.column('c1', width=250, anchor='center')

tree.column('c2', width=250, anchor='center')

tree.column('c3', width=250, anchor='center')

tree.column('c4', width=250, anchor='center')



tree.heading('c1', text='姓名')

tree.heading('c2', text='时间')

tree.heading('c3', text='电话')

tree.heading('c4', text='内容')


tree.pack(side=tkinter.LEFT, fill=tkinter.Y)

#Treeview组件与垂直滚动条结合

scrollBar.config(command=tree.yview)
#T = Text(top, height=100, width=400)

#定义并绑定Treeview组件的鼠标单击事件

def treeviewClick(event):
    #T.insert(END, "aaaaa")
    pass

tree.bind('<Button-1>', treeviewClick)



#插入演示数据
a = sqll.sqlse("1")
a1=0;
for i in a:
    a1+=a1
    tree.insert('', a1, values=(i["phonenum"],i["smsdate"],i["sms_id"],i["sms"]))
top.title("短信查看器")


#T.pack()
top.mainloop()
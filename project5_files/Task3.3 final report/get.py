try:
    o = open('ave_TJ.txt','w+')

    with open('TJ.txt','r')as f:
        x = f.readlines()
        total = 0
        length = len(x)
        for item in x:
            y = item.split()
            total += float(y[1])
        result = total/length
        o.write(str(result))


    o.close()
    f.close()
except:
    print("Python Error!")

try:
    o = open('ave_TS.txt','w+')

    with open('TS.txt','r')as f:
        x = f.readlines()
        total = 0
        length = len(x)
        for item in x:
            y = item.split()
            total += float(y[1])
        result = total/length
        o.write(str(result))


    o.close()
    f.close()
except:
    print("Python Error!")

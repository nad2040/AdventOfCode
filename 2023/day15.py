seq = []

def parse():
    global seq
    with open("input15") as f:
        file = f.read().strip()
        seq = file.split(",")

def HASH(string):
    value = 0
    for c in string:
        value += ord(c)
        value *= 17
        value %= 256
    return value

def part1():
    global lines
    total = 0
    for step in seq:
        total += HASH(step)
        print(step,HASH(step))
    print(total)

def part2():
    boxes = [[] for _ in range(256)]
    for instr in seq:
        if instr[-1] == '-':
            label = instr[:-1]
            label_hash = HASH(label)
            for i,(lbl,_) in enumerate(boxes[label_hash]):
                if lbl == label:
                    boxes[label_hash].pop(i)
                    break
        else:
            instr = instr.split('=')
            label = instr[0]
            focal_length = int(instr[1])
            label_hash = HASH(label)
            found = False
            for i,(lbl,_) in enumerate(boxes[label_hash]):
                if lbl == label:
                    found = True
                    boxes[label_hash].pop(i)
                    boxes[label_hash].insert(i,(label,focal_length))
                    break
            if not found:
                boxes[label_hash].append((label,focal_length))

    total = 0
    for i,box in enumerate(boxes,1):
        for j,(_,fcl_len) in enumerate(box,1):
            total += i * j * fcl_len
    print(total)





parse()
part1()
part2()

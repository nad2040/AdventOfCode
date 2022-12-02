
rock = 1 
paper = 2 
scissors = 3 
loss = 0
draw = 3 
win = 6


def checkMove(a,b):
    if (b-1)%3==a:
        return win
    elif b==a:
        return draw
    elif (b+1)%3==a:
        return loss

def part1():
    sum = 0
    with open("input2.txt") as f:
        for line in f:
            line = line.strip() 
            moves = line.split()
            moves[0] = ord(moves[0])-ord("A")
            moves[1] = ord(moves[1])-ord("X")
            pts = moves[1]+1 + checkMove(moves[0],moves[1])
            sum += pts
        
    print(sum)

def part2():
    sum = 0
    with open("input2.txt") as f:
        for line in f:
            line = line.strip() 
            moves = line.split()
            moves[0] = ord(moves[0])-ord("A")
            moves[1] = ord(moves[1])-ord("X")
            move = 0
            if moves[1] == 0:
                move = (moves[0]-1)%3 
            elif moves[1] == 1:
                move = moves[0]
            elif moves[1] == 2:
                move = (moves[0]+1)%3

                
            pts = move+1 + 3*moves[1]
            sum += pts
        
    print(sum) 


part1()
part2()

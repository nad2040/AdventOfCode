path = []
root = {}
sizes = {}

def parse():
    with open("input7.txt") as f:
        for line in f:
            if line.split()[0] == '$':
                if line.split()[1] == 'cd':
                    if line.split()[2] == '/':
                        path = []
                    elif line.split()[2] == '..':
                        path = path[:-1]
                    else:
                        path += [line.split()[2]]
            elif line.split()[0].isdigit():
                directory = root
                for dir in path:
                    if dir not in directory:
                        directory[dir] = {}
                    directory = directory[dir]
                directory[line.split()[1]] = int(line.split()[0])


def findsum(directory,path='/'):
    sum = 0
    for dir in directory:
        if isinstance(directory[dir],int):
            sum += directory[dir]
        else:
            sum += findsum(directory[dir],path+dir+'/')
    sizes[path] = sum
    return sum


def part1():
    sum = 0
    for dir in sizes:
        if sizes[dir] < 100000:
            sum += sizes[dir]

    print(sum)


def part2():
    total = 70_000_000
    current = sizes["/"]
    current_free = total - current
    target_free = 30_000_000
    target_deletion = target_free - current_free
    min = total
    for dir in sizes:
        if sizes[dir] >= target_deletion and sizes[dir] < min:
            min = sizes[dir]

    print(min)

    

parse()
findsum(root)
# print(root)
# print(sizes)
part1()
part2()

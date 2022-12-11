
class Monkey:
    number = -1
    items = []
    operation = lambda x:x
    divisible = -1
    true_monkey = -1
    false_monkey = -1

    def __init__(self, number, items, operation, divisible, true_monkey, false_monkey):
        self.number = number
        self.items = items
        self.operation = operation
        self.divisible = divisible
        self.true_monkey = true_monkey
        self.false_monkey = false_monkey
        self.inspected = 0
    
    def __str__(self):
        return f"{self.number = }\n{self.items = }\n{self.operation = }\n{self.divisible = }\n{self.true_monkey = }, {self.false_monkey = }\n{self.inspected = }" 

    def take_turn(self, part1=True, product=1):
        if part1:
            product *= 3
        throws = []
        while len(self.items) > 0:
            self.inspected += 1
            item = self.items[0]
            self.items = self.items[1:]
            item = self.operation(item)
            if part1:
                item = item // 3
            item = item % product
            if item % self.divisible == 0:
                throws.append((self.true_monkey,item))
            else:
                throws.append((self.false_monkey,item))

        return throws

monkeys = []
product = 1
def parse():
    global monkeys, product
    monkeys = []
    product = 1
    with open("input11.txt") as f:
        lines = f.readlines()
        number,items,operation,divisible,true_monkey,false_monkey = 0,[],None,0,0,0
        for i in range(len(lines)):
            line = lines[i].strip()
            line = line.split()
            if i%7 == 0:
                number = int(line[1][0])
                # print(f"{number = }")
            elif i%7 == 1:
                for j in range(len(line)-2):
                    items.append(int(line[j+2].replace(",","")))
                # print(f"{items = }")
            elif i%7 == 2:
                op = line[4]
                oper2 = line[5]
                if oper2 == "old":
                    operation = lambda x: x**2 
                else:
                    oper2 = int(oper2)
                    if op == "+":
                        operation = lambda x,operand=oper2: x + operand
                    else:
                        operation = lambda x,operand=oper2: x * operand
            elif i%7 == 3:
                divisible = int(line[3])
                product *= divisible
            elif i%7 == 4:
                true_monkey = int(line[5])
            elif i%7 == 5:
                false_monkey = int(line[5])
                monkeys += [Monkey(number,items,operation,divisible,true_monkey,false_monkey)]
                number,items,operation,divisible,true_monkey,false_monkey = 0,[],None,0,0,0
   

def part1():
    global monkeys,product

    for round in range(20):
        for m in range(len(monkeys)):
            turn = monkeys[m].take_turn(True,product)
            for monkey, item in turn:
                monkeys[monkey].items.append(item)

    sorted_list = monkeys.copy()
    sorted_list.sort(key=lambda m: m.inspected,reverse=True)
    print(sorted_list[0].inspected * sorted_list[1].inspected)


def part2():
    global monkeys
    for round in range(10000):
        for m in range(len(monkeys)):
            turn = monkeys[m].take_turn(False,product)
            for monkey, item in turn:
                monkeys[monkey].items.append(item)


    sorted_list = monkeys.copy()
    sorted_list.sort(key=lambda m: m.inspected,reverse=True)
    print(sorted_list[0].inspected * sorted_list[1].inspected)

parse()
part1()
parse()
part2()
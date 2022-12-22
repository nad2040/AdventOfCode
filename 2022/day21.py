monkeys = {}

def parse():
    with open("input21.txt") as f:
        for line in f:
            line = line.strip()
            line = line.split(": ")
            monkeys[line[0]] = int(line[1]) if line[1].isdigit() else line[1]

class AST:
    def __init__(self,parent=None,token=None,left=None,right=None):
        self.parent = parent
        self.token = token
        self.left = left
        self.right = right

    def evaluate(self):
        if isinstance(self.token, int):
            return self.token
        else:
            if self.token == "+":
                return self.left.evaluate() + self.right.evaluate()
            elif self.token == "-":
                return self.left.evaluate() - self.right.evaluate()
            elif self.token == "*":
                return self.left.evaluate() * self.right.evaluate()
            else:
                return self.left.evaluate() // self.right.evaluate()

    def walk(self,max_depth=-1):
        stack = [(self,max_depth)]

        while len(stack) > 0:
            ast,max_depth = stack.pop()

            yield ast,max_depth
            if max_depth == 0:
                continue
            if isinstance(ast.token,str) and ast.token != "X":
                stack.append((ast.right,max_depth-1))
                stack.append((ast.left,max_depth-1))

    def __str__(self):
        return f"(token={self.token})"

    def print_tree(self):
        for n,d in self.walk():
            print("  "*(-d-1)+str(n))

    def find_x(self):
        for n,_ in self.walk():
            if n.token == "X":
                return n


def build_tree(parent=None,monkey="root",part2=False):
    if part2 and monkey == "humn":
        return AST(parent=parent,token="X")
    if isinstance(monkeys[monkey],int):
        return AST(parent=parent,token=monkeys[monkey])
    else:
        expr = monkeys[monkey].split()
        ast = AST(parent=parent,token=expr[1])
        left = build_tree(ast,expr[0],part2)
        right = build_tree(ast,expr[2],part2)
        ast.left = left
        ast.right = right
        return ast

def part1():
    tree = build_tree()
    print(tree.evaluate())

def part2():
    tree = build_tree(part2=True)
    # tree.print_tree()
    traveler = tree.find_x()

    ast = AST()
    root_node = ast
    while traveler.parent.parent != None:
        left = bool(traveler == traveler.parent.left)
        other_value = traveler.parent.right.evaluate() if left else traveler.parent.left.evaluate()
        if traveler.parent.token == "+":
            ast.token = "-"
            ast.left = AST(parent=ast)
            ast.right = AST(parent=ast,token=other_value)
            ast = ast.left
        elif traveler.parent.token == "*":
            ast.token = "/"
            ast.left = AST(parent=ast)
            ast.right = AST(parent=ast,token=other_value)
            ast = ast.left
        elif traveler.parent.token == "-":
            if left:
                ast.token = "+"
                ast.left = AST(parent=ast)
                ast.right = AST(parent=ast,token=other_value)
                ast = ast.left
            else:
                ast.token = "-"
                ast.left = AST(parent=ast,token=other_value)
                ast.right = AST(parent=ast)
                ast = ast.right
        else:
            if left:
                ast.token = "*"
                ast.left = AST(parent=ast)
                ast.right = AST(parent=ast,token=other_value)
                ast = ast.left
            else:
                ast.token = "/"
                ast.left = AST(parent=ast,token=other_value)
                ast.right = AST(parent=ast)
                ast = ast.right
       
        traveler = traveler.parent

    other_value = traveler.parent.right.evaluate() if traveler == traveler.parent.left else traveler.parent.left.evaluate()
    ast.token = other_value

    # root_node.print_tree()
    print(root_node.evaluate())

parse()
part1()
part2()

# node = "root"
# intermediary = [node]
# stack = []
# while len(intermediary) > 0:
#     node = intermediary.pop()
#     if isinstance(monkeys[node],int):
#         stack.append(monkeys[node])
#     else:
#         expr = monkeys[node].split() 
#         intermediary.append(expr[2])
#         intermediary.append(expr[0])
#         stack.append(expr[1])
#
# print(evaluate(reverse_polish(stack)))

# def flatten(xs):
#     for x in xs:
#         if isinstance(x, list):
#             yield from flatten(x)
#         else:
#             yield x

# def reverse_polish(stack):
#     reverse = []
#     while len(stack) > 0:
#         token = stack.pop()
#         if isinstance(token,str):
#             # print("token is ")
#             n1 = reverse.pop()
#             # print("pop",n1)
#             n2 = reverse.pop()
#             # print("pop",n2)
#             reverse.append([n1,n2,token])
#         else:
#             # print("push",token)
#             reverse.append(token)
#
#     return list(flatten(reverse))

# def evaluate(queue):
#     stack = []
#     while len(queue) > 0:
#         token = queue.pop(0)
#         if isinstance(token,int):
#             stack.append(token)
#         else:
#             op2 = stack.pop()
#             op1 = stack.pop()
#             if token == "+":
#                 stack.append(op1 + op2)
#             elif token == "-":
#                 stack.append(op1 - op2)
#             elif token == "*":
#                 stack.append(op1 * op2)
#             else:
#                 stack.append(op1 // op2)
#     
#     return stack.pop()

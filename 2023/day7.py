FIVE_OF_A_KIND = 6
FOUR_OF_A_KIND = 5
FULL_HOUSE = 4
THREE_OF_A_KIND = 3
TWO_PAIR = 2
ONE_PAIR = 1
HIGH_CARD = 0

def parse_hand(hand):
    s = set(hand)
    if len(s) == 1:
        return (hand,FIVE_OF_A_KIND)
    if len(s) == 4:
        return (hand,ONE_PAIR)
    if len(s) == 5:
        return (hand,HIGH_CARD)

    counts = []
    for c in s:
        counts.append(hand.count(c))
    counts.sort()
    counts = tuple(counts)

    if counts == (1,2,2):
        return (hand,TWO_PAIR)
    if counts == (1,1,3):
        return (hand,THREE_OF_A_KIND)
    if counts == (2,3):
        return (hand,FULL_HOUSE)
    if counts == (1,4):
        return (hand,FOUR_OF_A_KIND)
    print("err")
    return (hand,-1)

def parse_hand2(hand, current):
    s = set(hand)

    counts = []
    jacks = hand.count('J')
    for c in s:
        if c != 'J':
            counts.append(hand.count(c))
    counts.sort()
    counts = tuple(counts)

    if jacks == 0:
        return (hand,current)
    if counts == (1,1,1,1):
        return (hand,ONE_PAIR)
    # I'm not sure you can promote to a two-pair ever
    if counts == (1,1,2) or counts == (1,1,1):
        return (hand,THREE_OF_A_KIND)
    if counts == (2,2):
        return (hand,FULL_HOUSE)
    if counts == (1,3) or counts == (1,2) or counts == (1,1):
        return (hand,FOUR_OF_A_KIND)
    if counts == () or counts == (4,) or counts == (3,) or counts == (2,) or counts == (1,):
        return (hand,FIVE_OF_A_KIND)

    print("err",hand)
    return (hand,-1)

def compare_hand():
    def cmp1(hand_and_bid1,hand_and_bid2):
        hand1,kind1 = hand_and_bid1[0]
        hand2,kind2 = hand_and_bid2[0]
        if kind1 > kind2:
            return 1
        if kind1 < kind2:
            return -1
        cards="23456789TJQKA"
        for c1,c2 in zip(list(hand1),list(hand2)):
            if cards.find(c1) == cards.find(c2):
                continue
            else:
                return 1 if cards.find(c1) > cards.find(c2) else -1
        return 0

    def cmp2(hand_and_bid1,hand_and_bid2):
        hand1,kind1 = hand_and_bid1[0]
        hand2,kind2 = hand_and_bid2[0]
        hand1,kind1 = parse_hand2(hand1,kind1)
        hand2,kind2 = parse_hand2(hand2,kind2)
        if kind1 > kind2:
            return 1
        if kind1 < kind2:
            return -1
        cards="J23456789TQKA"
        for c1,c2 in zip(list(hand1),list(hand2)):
            if cards.find(c1) == cards.find(c2):
                continue
            else:
                return 1 if cards.find(c1) > cards.find(c2) else -1
        return 0

    return cmp1, cmp2


hands_and_bids = []

def parse():
    global hands_and_bids
    with open("input7") as f:
        for line in f:
            line = line.strip()
            hand_bid = line.split()
            hand = parse_hand(hand_bid[0])
            bid = int(hand_bid[1])
            hands_and_bids.append((hand,bid))

from functools import cmp_to_key

def part1():
    global hands_and_bids
    cmp1,_ = compare_hand()
    hands_and_bids.sort(key=cmp_to_key(cmp1))
    sum_ranks = 0
    for i,(_,bid) in enumerate(hands_and_bids,1):
        sum_ranks += i * bid
    print(sum_ranks)



def part2():
    global hands_and_bids
    _,cmp2 = compare_hand()
    hands_and_bids.sort(key=cmp_to_key(cmp2))
    sum_ranks = 0
    for i,(_,bid) in enumerate(hands_and_bids,1):
        sum_ranks += i * bid
    print(sum_ranks)

parse()
part1()
part2()

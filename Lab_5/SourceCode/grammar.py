class Grammar():
    def __init__(self):
        self.P = {
            'S': ['bA', 'BC'],
            'A': ['a', 'aS', 'bCaCa'],
            'B': ['A', 'bS', 'bCAa'],
            'C': ['eps', 'AB'],
            'D': ['AB']
        }
        self.V_N = ['S', 'A', 'B', 'C', 'D']
        self.V_T = ['a', 'b']

    def Remove_Epsilon(self):
        # 1. remove epsilon productions
        # find non-terminal symbols that derive into empty string
        nt_epsilon = []
        for key, value in self.P.items():
            s = key
            productions = value
            for p in productions:
                if p == 'eps':
                    nt_epsilon.append(s)

        for key, value in self.P.items():
            for ep in nt_epsilon:
                for v in value:
                    prod_copy = v
                    if ep in prod_copy:
                        for c in prod_copy:
                            # delete epsilon prod and add new prod
                            if c == ep:
                                value.append(prod_copy.replace(c, ''))
        # initialize a copy with added prod
        P1 = self.P.copy()
        # remove eps prod from copy
        for key, value in self.P.items():
            if key in nt_epsilon and len(value) < 2:
                del P1[key]
            else:
                for v in value:
                    if v == 'eps':
                        P1[key].remove(v)

        print(f"1. After removing epsilon productions:\n{P1}")
        self.P = P1.copy()
        return P1

    def Eliminate_Unit_Prod(self):
        # 2. Eliminate any remaining (unit productions)
        # new productions for next step
        P2 = self.P.copy()
        for key, value in self.P.items():
            # replace unit productions
            for v in value:
                if len(v) == 1 and v in self.V_N:
                    P2[key].remove(v)
                    for p in self.P[v]:
                        P2[key].append(p)
        print(f"2. After removing unit productions:\n{P2}")
        self.P = P2.copy()
        return P2

    def Eliminate_Inaccesible_Symbols(self):
        # 3. Eliminate inaccesible symbols
        P3 = self.P.copy()
        accesible_symbols = self.V_N
        # find elements that are inaccesible
        for key, value in self.P.items():
            for v in value:
                for s in v:
                    if s in accesible_symbols:
                        accesible_symbols.remove(s)
        # remove inaccesible symbols
        for el in accesible_symbols:
            del P3[el]
        print(f"3. After removing inaccesible symbols:\n{P3}")
        self.P = P3.copy()
        return P3

    def Remove_Nonproductive(self):
        # 4. Remove non-productive symbols
        P4 = self.P.copy()

        # Check the keys
        for key, value in self.P.items():
            count = 0
            # identify non-productive symbols
            for v in value:
                if len(v) == 1 and v in self.V_T:
                    count += 1
            # remove non-productive symbols
            if count == 0 and key not in self.V_T:
                del P4[key]
                for k, v in self.P.items():
                    for e in v:
                        if k == key:
                            break
                        else:
                            if key in e:
                                P4[k].remove(e)

                # Check the values
            for key, value in self.P.items():
                for v in value:
                    for c in v:
                        if c.isupper() and c not in P4.keys() and c != key:
                            P4[key].remove(v)
                            break

            print(f"4. After removing non-productive symbols:\n{P4}")
            self.P = P4.copy()
            return P4

    def Chomsky_Normal_Form(self):
        # 5. Obtain CNF
        P5 = self.P.copy()
        temp = {}
        vocabulary = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                      'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        free_symbols = [v for v in vocabulary if v not in self.P.keys()]
        for key, value in self.P.items():
            for v in value:
                if (len(v) == 1 and v in self.V_T) or (len(v) == 2 and v.isupper()):
                    continue
                else:
                    # split production into two parts
                    left = v[:len(v) // 2]
                    right = v[len(v) // 2:]
                    # get the new symbols for each half
                    if left in temp.values():
                        temp_key1 = ''.join([i for i in temp.keys() if temp[i] == left])
                    else:
                        temp_key1 = free_symbols.pop(0)
                        temp[temp_key1] = left
                    if right in temp.values():
                        temp_key2 = ''.join([i for i in temp.keys() if temp[i] == right])
                    else:
                        temp_key2 = free_symbols.pop(0)
                        temp[temp_key2] = right
                    # replace the production with the new symbols
                    P5[key] = [temp_key1 + temp_key2 if item == v else item for item in P5[key]]

        # add new productions
        for key, value in temp.items():
            P5[key] = [value]

        print(f"5. Final CNF:\n{P5}")
        return P5

    def Return_Productions(self):
        print(f"Initial Grammar:\n{self.P}")
        P1 = self.Remove_Epsilon()
        P2 = self.Eliminate_Unit_Prod()
        P3 = self.Eliminate_Inaccesible_Symbols()
        P4 = self.Remove_Nonproductive()
        P5 = self.Chomsky_Normal_Form()
        return P1, P2, P3, P4, P5
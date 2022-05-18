from my_fraction import Fraction

basis = []
checkfunction = []

def makeSolutionX(bs, coeff, solutions, matrix):
    solution = []
    for i in range(0, len(matrix[0])):
        solution.append(Fraction(0,1))
    for item in bs:
        solution[item[1]] = coeff[0][item[0]]
    solutions.append(solution)


def findGreatestSolution(solution):
    min = Fraction(999)
    index = -1
    for i in range(0, len(solution[0])):
        if solution[0][i] < Fraction(0, 1):
            if solution[0][i] < min:
                min = solution[0][i]
                index = i
    return index


def findSolve(solution, matrix, function):
    index = findGreatestSolution(solution)
    so = Fraction(-999)
    i = -1
    if(index != -1):
        for k in range(0, len(matrix[index])):
            if(matrix[index][k] < Fraction(0, 1)):
                if function[0][k] / matrix[index][k] > so:
                    so = function[0][k] / matrix[index][k]
                    i = k
    return index, i


def findNegative(function):
    max = Fraction(0)
    j = -1
    for i in range(0, len(function[0]) - 1):
        if Fraction(0, 1) > function[0][i]:
            if function[0][i] < max:
                max = function[0][i]
                j = i
    return j


def findPrevious(i):
    for item in basis:
        if item[0] == i:
            return item

def findPreviousByJ(j):
    for item in basis:
        if item[1] == j:
            return item

def rowInBasis(row):
    for item in basis:
        if item[0] == row:
            return True
    return False


def columnInBasis(column):
    for item in basis:
        if item[1] == column:
            return True
    return False


def createBasis(matrix, solution, function):
    for i in range(0, len(matrix)):
        if rowInBasis(i):
            continue
        for j in range(0, len(matrix[i])):
            if columnInBasis(j):
                continue
            basis.append((i, j))
            transformationJordan(
                basis[len(basis)-1], matrix, solution, function)
            break


def transformationJordan(element, matrix, solution, function):
    i = element[0]
    j = element[1]
    elementMatrix = matrix[i][j]
    for k in range(0, len(matrix[0])):
        matrix[i][k] /= elementMatrix
    solution[0][i] /= elementMatrix

    for c in range(0, len(matrix)):
        if c != i:
            newElementMatrix = matrix[c][j]
            for k in range(0, len(matrix[0])):
                matrix[c][k] -= matrix[i][k]*newElementMatrix
            solution[0][c] -= solution[0][i]*newElementMatrix
    newElementMatrix = function[0][j]
    for c in range(0, len(function[0]) - 1):
        function[0][c] -= matrix[i][c] * newElementMatrix
    function[0][len(function[0]) - 1] -= solution[0][i]*newElementMatrix


def isBasis(matrix, column, row):
    for i in range(0, len(matrix)):
        if i == row:
            continue
        else:
            if matrix[i][column] != Fraction(0, 1):
                return False
    return True


def findAndCreateBasisVar(matrix, function, solution):
    for i in range(0, len(matrix)):
        for j in range(0, len(matrix[i])):
            if(matrix[i][j] == Fraction(1, 1) or matrix[i][j] == Fraction(-1, 1)):
                if isBasis(matrix, j, i) and function[0][j] == Fraction(0, 1):
                    basis.append((i, j))
                    if(matrix[i][j] == -1):
                        for k in range(0, len(matrix[i])):
                            matrix[i][k] *= Fraction(-1)
                        solution[0][i] *= Fraction(-1)



def printFunction(function):
    print("Z", end="     ")
    print(function[0][len(function[0]) - 1], end="")
    for i in range(0, len(function[0])-1):
        print("     ", function[0][i], end="")


def printMatrix(matrix, solution, function):
    print("")
    print("б.п", end="    ")
    print("1", end="    ")
    for i in range(0, len(matrix[0])):
        print("x" + str(i + 1), end="      ")
    print("")
    for i in range(0, len(matrix)):
        print("\n", end="      ")
        print(solution[0][i], end="      ")
        for j in range(0, len(matrix[i])):
            print(matrix[i][j], end="      ")
    print("")
    printFunction(function)
    print("")

def findSimplexRatio(matrix, function, j):
    so = Fraction(999)
    i = -1
    for k in range(0, len(matrix)):
        if matrix[k][j] > Fraction(0,1):
            if function[0][k]/matrix[k][j] < so:
                so = function[0][k]/matrix[k][j]
                i = k

    return i

def printSol(solutions, checkfunction):
    for i in range(0,len(solutions[0])):
        if checkfunction[i]:
            print(f"x{i+1}={solutions[len(solutions)-1][i]}", end=" ")

def inBasis(j, bs):
    for item in bs:
        if item[1] == j:
            return True
    return False

def checkMultipleSolution(function, bs):
    j = -1
    for i in range(0,len(function[0])-1):
        if function[0][i] == Fraction(0,1) and not(inBasis(i, bs)):
            j = i
    return j

def printMulSolution(solutions, chFunction):
    fsl = []
    for i in range(0, len(solutions[0])):
        #(1-λ)X1 + VX2 =X1 - VX1 + VX2 = X1 + λ(-X1+X2)
        fsl.append(solutions[len(solutions)-2][i]*Fraction(-1) + solutions[len(solutions) -1][i])
    for i in range(0, len(fsl)):
        if solutions[len(solutions)-2][i] == Fraction(0):
            if fsl[i]==Fraction(0):
                print(f"x{i+1}=0")
            else:
                print(f"x{i+1}={fsl[i]}λ")
        elif fsl[i] == Fraction(0):
            print(f"x{i+1}={fsl[i]}λ")
        elif fsl[i] == Fraction(0) and solutions[len(solutions)-2][i] == Fraction(0):
            print(f"x{i+1}=0")
        else:
            print(f"x{i+1}={solutions[len(solutions)-2][i]}", end="")
            if fsl[i] < Fraction(0):
                print(f" - {fsl[i]*Fraction(-1)}λ")
            elif fsl[i] > Fraction(0):
                print(f" + {fsl[i]}λ")
            else:
                print("0")


def createMatrix():
    with open("2.txt") as f:
        functionZ_in_file = f.readline()
        solution_in_file = f.readline()
        matrix_in_file = f.readlines()

    functionZ = []
    functionZ.append([Fraction(x) for x in map(lambda item: int(item),
                                               functionZ_in_file.split(" "))])
    for i in range(0, len(functionZ[0]) - 1):
        functionZ[0][i] *= Fraction(-1)
        if functionZ[0][i] != Fraction(0):
            checkfunction.append(True)
        else:
            checkfunction.append(False)
    print(functionZ)
    matrix = []
    for line in matrix_in_file:
        matrix.append([Fraction(x) for x in map(
            lambda item: int(item), line.split(" "))])
    solution = []
    solution.append([Fraction(x) for x in map(
        lambda item: int(item), solution_in_file.split(" "))])
    

    printMatrix(matrix, solution, functionZ)
    findAndCreateBasisVar(matrix, functionZ, solution)
    printMatrix(matrix, solution, functionZ)
    if len(basis) != len(matrix):
        createBasis(matrix, solution, functionZ)
        printMatrix(matrix, solution, functionZ)
    if findNegative(functionZ) != -1:
        print("Двойственно недопустимая")
        return
    printMatrix(matrix, solution, functionZ)
    print(f"\nБазис: {basis}")
    
    solutions = []
    makeSolutionX(basis, solution, solutions, matrix)
    while(True):
        if findSolve(solution, matrix, functionZ) != (-1, -1):
            i, j = findSolve(solution, matrix, functionZ)
            if j == -1:
                print("Система не ограничена")
                break
            oldBasisVar = findPrevious(i)
            basis.remove(oldBasisVar)
            basis.append((i, j))
            basis.sort()
            transformationJordan((i, j), matrix, solution, functionZ)
            makeSolutionX(basis, solution, solutions, matrix)
            printMatrix(matrix, solution, functionZ)
            print(f"\nБазис: {basis}")
            printSol(solutions, checkfunction)
        else:
            if checkMultipleSolution(functionZ, basis) != -1:
                j = checkMultipleSolution(functionZ, basis)
                i = findSimplexRatio(matrix, functionZ, j)
                oldBasisVar = findPrevious(i)
                basis.remove(oldBasisVar)
                basis.append((i, j))
                basis.sort()
                transformationJordan((i, j), matrix, solution, functionZ)
                makeSolutionX(basis, solution, solutions, matrix)
                printMatrix(matrix, solution, functionZ)
                print(f"\nbasis: {basis}")
                printMulSolution(solutions, checkfunction)
                print("\n Z = ", (functionZ[0][len(functionZ[0]) - 1]))
            else:
                printSol(solutions, checkfunction)
                print("\n Z = ", (functionZ[0][len(functionZ[0]) - 1]))
            break


def main():
    createMatrix()

if __name__ == "__main__":
    main()
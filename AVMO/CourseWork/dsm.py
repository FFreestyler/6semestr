from my_fraction import Fraction

basis = []
const = 999999


# функция создает решение для определенного базиса
def make_solution_by_x(bs, coeff, solutions, matrix):
    solution = []
    for _ in range(0, len(matrix[0])):
        solution.append(Fraction(0, 1))
    for item in bs:
        solution[item[1]] = coeff[0][item[0]]
    solutions.append(solution)


# находит наименьший в строке свободных членов(столбец "1")
def find_greatest_in_solution(solution):
    min = Fraction(const)
    index = const
    for i in range(0, len(solution[0])):
        if solution[0][i] < Fraction(0, 1):
            if solution[0][i] < min:
                min = solution[0][i]
                index = i
    return index


# Находит элемент взяв минимальный из строки свободных членов и из столбца SO(тут же его и считаем)
def find_solve(solution, matrix, function):
    index = find_greatest_in_solution(solution)
    so = Fraction(-const)
    i = const
    if(index != const):
        for k in range(0, len(matrix[index])):
            if(matrix[index][k] < Fraction(0, 1)):
                if function[0][k] / matrix[index][k] > so:
                    so = function[0][k] / matrix[index][k]
                    i = k
    return index, i


def find_negative_in_z(function):  # нахождение отрицательных чисел в строке Z
    max = Fraction(0)
    j = const
    for i in range(0, len(function[0]) - 1):
        if Fraction(0, 1) > function[0][i]:
            if function[0][i] < max:
                max = function[0][i]
                j = i
    return j


def find_removable_basis_var(i):  # Находим базис, который нужно будет удалить
    for item in basis:
        if item[0] == i:
            return item


# проверка на то, является ли элемент по строке или столбцу базисным
def column_or_row_in_basis(column, row):
    for item in basis:
        if item[1] == column or item[0] == row:
            return True
    return False


# добавляем найденный базис в массив базисов
def create_basis(matrix, solution, function):
    for i in range(0, len(matrix)):
        if column_or_row_in_basis(i, 0):
            continue
        for j in range(0, len(matrix[i])):
            if column_or_row_in_basis(0, j):
                continue
            basis.append((i, j))
            jordan(
                basis[len(basis)-1], matrix, solution, function)
            break


def jordan(element, matrix, solution, function):  # Метод Джордана
    i = element[0]
    j = element[1]
    element_of_matrix = matrix[i][j]
    for k in range(0, len(matrix[0])):
        matrix[i][k] /= element_of_matrix
    solution[0][i] /= element_of_matrix

    for c in range(0, len(matrix)):
        if c != i:
            new_element_of_matrix = matrix[c][j]
            for k in range(0, len(matrix[0])):
                matrix[c][k] -= matrix[i][k]*new_element_of_matrix
            solution[0][c] -= solution[0][i]*new_element_of_matrix
    new_element_of_matrix = function[0][j]
    for c in range(0, len(function[0]) - 1):
        function[0][c] -= matrix[i][c] * new_element_of_matrix
    function[0][len(function[0]) - 1] -= solution[0][i]*new_element_of_matrix


# проверка на то, является ли элемент базисным, тоесть под ним или над ним нули
def is_basis(matrix, column, row):
    for i in range(0, len(matrix)):
        if i == row:
            continue
        else:
            if matrix[i][column] != Fraction(0, 1):
                return False
    return True


def print_matrix(matrix, solution, function):  # вывод матрицы
    print("\n\n")
    print("Simplex table: ")
    for i in range(0, len(matrix[0])):
        print("%-15s" % ('x' + str(i + 1)), end="")
    print("")
    print("______________________________________________________________")
    for i in range(0, len(matrix)):
        print("\n", end="")
        for j in range(0, len(matrix[i])):
            print("%-15s" % (str(matrix[i][j])), end="")
        print(solution[0][i], end="        ")
    print("\n")
    print("______________________________________________________________")
    for i in range(0, len(function[0])):
        print("%-15s" % (str(function[0][i])),  end="")
    print("")


# нахождение симплекс отношения(похожа на функцию выше, но нужна для множества решений)
def find_simplex_ratio(matrix, function, j):
    so = Fraction(999)
    i = const
    for k in range(0, len(matrix)):
        if matrix[k][j] > Fraction(0, 1):
            print(function[0][k]/matrix[k][j])
            if function[0][k]/matrix[k][j] < so:
                so = function[0][k]/matrix[k][j]
                i = k
    return i


def in_basis(j):  # проверка на то, находится ли столбец в базисе
    for item in basis:
        if item[1] == j:
            return True
    return False


# проверка на то, есть ли множество решений в симплекс таблице
def check_multiply_solutions(function):
    j = const
    for i in range(0, len(function[0])-1):
        if function[0][i] == Fraction(0, 1) and not(column_or_row_in_basis(i, -const)):
            j = i
    return j


# основная функция, здесь происходит к поиск разрешающего элемента, и его преобразование в базис
def matrix_decision(solution, matrix, functionZ, solutions):
    while(True):
        if find_solve(solution, matrix, functionZ) != (const, const):
            i, j = find_solve(solution, matrix, functionZ)
            if j == const:
                print("Система не ограничена")
                break
            removable_basis_var = find_removable_basis_var(i)
            basis.remove(removable_basis_var)
            basis.append((i, j))
            basis.sort()
            jordan((i, j), matrix, solution, functionZ)
            make_solution_by_x(basis, solution, solutions, matrix)
            print_matrix(matrix, solution, functionZ)
            print("\nZ = ", (functionZ[0][len(functionZ[0]) - 1]))
        else:
            if check_multiply_solutions(functionZ) != const:
                j = check_multiply_solutions(functionZ)
                i = find_simplex_ratio(matrix, functionZ, j)
                removable_basis_var = find_removable_basis_var(i)
                basis.remove(removable_basis_var)
                basis.append((i, j))
                basis.sort()
                jordan((i, j), matrix, solution, functionZ)
                make_solution_by_x(basis, solution, solutions, matrix)
                print_matrix(matrix, solution, functionZ)
                multiply_sol = []
                for i in range(0, len(solutions[0])):
                    multiply_sol.append(
                        Fraction(-1, 1)*solutions[len(solutions)-2][i] + solutions[len(solutions)-1][i])
                print("Solution = [", end="")
                for i in range(0, len(multiply_sol)):
                    if multiply_sol[i] > Fraction(0, 1):
                        print(
                            f"{solutions[len(solutions)-2][i]} + {multiply_sol[i]}k", end="")
                    else:
                        print(
                            f"{solutions[len(solutions)-2][i]} - {Fraction(-1,1)*multiply_sol[i]}k", end="")
                    if i < len(multiply_sol) - 1:
                        print("; ", end="")
                print("]")
            break


def createMatrix():  # считывание матрицы
    with open("test.txt") as f:
        function_z_in_file = f.readline()
        solution_in_file = f.readline()
        matrix_in_file = f.readlines()

    function_z = []
    function_z.append([Fraction(x) for x in map(lambda item: int(item),
                                                function_z_in_file.split(" "))])
    for i in range(0, len(function_z[0]) - 1):
        function_z[0][i] *= Fraction(-1)
    print(function_z)
    solution = []
    solution.append([Fraction(x) for x in map(
        lambda item: int(item), solution_in_file.split(" "))])
    matrix = []
    for line in matrix_in_file:
        matrix.append([Fraction(x) for x in map(
            lambda item: int(item), line.split(" "))])

    print_matrix(matrix, solution, function_z)
    for i in range(0, len(matrix)):  # findAndCreateBasisVar
        for j in range(0, len(matrix[i])):
            if(matrix[i][j] == Fraction(1, 1) or matrix[i][j] == Fraction(-1, 1)):
                if is_basis(matrix, j, i) and function_z[0][j] == Fraction(0, 1):
                    basis.append((i, j))
                    if(matrix[i][j] == -1):
                        for k in range(0, len(matrix[i])):
                            matrix[i][k] *= Fraction(-1)
                        solution[0][i] *= Fraction(-1)
    print_matrix(matrix, solution, function_z)
    if len(basis) != len(matrix):
        create_basis(matrix, solution, function_z)
        print_matrix(matrix, solution, function_z)
    if find_negative_in_z(function_z) != const:
        print("Двойственно недопустимая")
        return
    print_matrix(matrix, solution, function_z)

    solutions = []
    make_solution_by_x(basis, solution, solutions, matrix)
    matrix_decision(solution, matrix, function_z, solutions)


def main():
    createMatrix()


if __name__ == "__main__":
    main()

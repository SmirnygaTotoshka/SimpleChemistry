
package smirnygatotoshka.ru.simplechemistry;

import android.util.Log;

public class Matrix
{
int rows;
int Column;
float[][] cells;

public Matrix(int rows, int Column)
{
	this.rows = rows;
	this.Column = Column;
	cells = new float[rows][Column];
}

/* Private helper functions for gaussJordanEliminate()*/ 

/** Swaps the two rows of the given indices in this matrix. The degenerate
 case of i == j is allowed.*/
private void swapRows(int rows1, int rows2)
{
	
	float[] temp = cells[rows1];
	cells[rows1] = cells[rows2];
	cells[rows2] = temp;
}

/** Returns a new row that is the sum of the two given rows. The rows are not
 indices. This object's data is unused.
 For example, addRow([3, 1, 4], [1, 5, 6]) = [4, 6, 10].*/
private float[] addRows(float[] fs, float[] fs2)
{
	float[] z = fs.clone();
	for (int i = 0; i < z.length; i++)
		z[i] = fs[i] + fs2[i];
	return z;
}

/** Returns a new row that is the product of the given row with the given
 scalar. The row is is not an index. This object's data is unused.
 For example, multiplyRow([0, 1, 3], 4) = [0, 4, 12].*/
private float[] multiplyRow(float[] cells2, float f)
{
	float[] y = cells2.clone();
	for (int i = 0; i < y.length; i++)
		y[i] = cells2[i] * f;
	return y;
}

/** Returns the GCD of all the numbers in the given row. The row is is not an
 index. This object's data is unused.
 For example, gcdRow([3, 6, 9, 12]) = 3.*/
private float gcdRow(float[] x)
{
	float result = 0;
	for (int i = 0; i < x.length; i++)
		result = gcd(x[i], result);
	return result;
}

/** Returns the greatest common divisor of the given integers.*/

public float gcd(float a, float b)
{
	while (b != 0)
	{
		float tmp = a % b;
		a = b;
		b = tmp;
	}
	return a;
}

/** Returns a new row where the leading non-zero number (if any) is positive,
 and the GCD of the row is 0 or 1. This object's data is unused.
 For example, simplifyRow([0, -2, 2, 4]) = [0, 1, -1, -2].*/
private float[] simplifyRow(float[] x)
{
	int sign = 0;
	for (int i = 0; i < x.length; i++)
	{
		if (x[i] > 0)
		{
			sign = 1;
			break;
		}
		else if (x[i] < 0)
		{
			sign = -1;
			break;
		}
	}
	float[] y = x.clone();
	if (sign == 0) return y;
	float g = gcdRow(x) * sign;
	for (int i = 0; i < y.length; i++)
		y[i] /= g;
	return y;
}

/** Changes this matrix to reduced row echelon form (RREF), except that each
 leading coefficient is not necessarily 1. Each row is simplified.*/
public void gaussJordanEliminate()
{
	// Simplify all rows
	Log.d(SimpleChemistry.TAG, write_matrix());
	
	for (int i = 0; i < rows; i++)
		cells[i] = simplifyRow(cells[i]);
		
	Log.d(SimpleChemistry.TAG, write_matrix());
	// Compute row echelon form (REF)
	int numPivots = 0;
	for (int i = 0; i < Column; i++)
	{
		// Find pivot
		int pivotRow = numPivots;
		
		while (pivotRow < rows && cells[pivotRow][i] == 0)
			pivotRow++;
			
		if (pivotRow == rows) continue;
		
		float pivot = cells[pivotRow][i];
		swapRows(numPivots, pivotRow);
		numPivots++;
		
		Log.d(SimpleChemistry.TAG, write_matrix());
		// Eliminate below
		
		for (int j = numPivots; j < rows; j++)
		{
			float g = gcd(pivot, cells[j][i]);
			cells[j] = simplifyRow(addRows(multiplyRow(cells[j], pivot / g), multiplyRow(cells[i], -cells[j][i] / g)));
		}
		Log.d(SimpleChemistry.TAG, write_matrix());
	}
	
	Log.d(SimpleChemistry.TAG, write_matrix());
	// Compute reduced row echelon form (RREF), but the leading coefficient
	// need not be 1
	for (int i = rows - 1; i >= 0; i--)
	{
		// Find pivot
		int pivotCol = 0;
		
		while (pivotCol < Column && cells[i][pivotCol] == 0)
			pivotCol++;
			
		if (pivotCol == Column) continue;
		
		float pivot = cells[i][pivotCol];
		// Eliminate above
		for (int j = i - 1; j >= 0; j--)
		{
			float g = gcd(pivot, cells[j][pivotCol]);
			cells[j] = simplifyRow(
					addRows(multiplyRow(cells[j], pivot / g), multiplyRow(cells[i], -cells[j][pivotCol] / g)));
		}
	}
	Log.d(SimpleChemistry.TAG, write_matrix());
};

/** Returns a string representation of this matrix, for debugging purposes.*/
public String write_matrix()
{
	String result = "[";
	for (int i = 0; i < rows; i++)
	{
		if (i != 0) result += "],\n";
		result += "[";
		for (int j = 0; j < Column; j++)
		{
			if (j != 0) result += ", ";
			result += cells[i][j];
		}
		result += "]";
	}
	return result + "]";
}

public int getRows()
{
	return rows;
}

public int getColumn()
{
	return Column;
}

public float getValue(int row, int Column)
{
	return cells[row][Column];
}

public void setValue(int row, int Column, float value)
{
	cells[row][Column] = value;
};
}

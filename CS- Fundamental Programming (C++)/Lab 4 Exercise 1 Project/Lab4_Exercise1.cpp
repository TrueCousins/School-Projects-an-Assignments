// Lab 4 Exercise 1
// Test of 4 if statements. 
//
// You will be explaining why the 4 if statements return true or false. 
//
// Program written by: Don Vogel

#include <iostream>

using namespace std;

int main()
{
	// We have 4 if statements in this program. You need to decide why the if statements output either 
	// true or false.  

	// First we need to declare some variable and their values
	int a = 2, b = 3, c = 4, d = 5;

	// output the values
	cout << "a=" << a << ",b=" << b << ",c=" << c << ",d=" << d << endl;

	// If statement number 1
	if (b + 2 >= d)
	{
		cout << "If number 1 is true" << endl;
	}
	else
	{
		cout << "If number 1 is false" << endl;
	}

	// output the values
	cout << "a=" << a << ",b=" << b << ",c=" << c << ",d=" << d << endl;

	// If statement number 2
	if (d == 5)
	{
		cout << "If number 2 is true" << endl;
	}
	else
	{
		cout << "If number 2 is false" << endl;
	}

	// output the values
	cout << "a=" << a << ",b=" << b << ",c=" << c << ",d=" << d << endl;

	// If statement number 3
	if (a > b || c <= d)
	{
		cout << "If number 3 is true" << endl;
	}
	else
	{
		cout << "If number 3 is false" << endl;
	}

	// output the values
	cout << "a=" << a << ",b=" << b << ",c=" << c << ",d=" << d << endl;

	// If statement number 4
	if (!(d = 4))
	{
		cout << "If number 4 is true" << endl;
	}
	else
	{
		cout << "If number 4 is false" << endl;
	}

	// output the values
	cout << "a=" << a << ",b=" << b << ",c=" << c << ",d=" << d << endl;


	return 0;
}
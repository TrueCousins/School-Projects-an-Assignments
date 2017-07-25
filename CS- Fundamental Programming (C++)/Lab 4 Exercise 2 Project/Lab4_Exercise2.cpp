// Lab 4 Exercise 2
// History grading project

#include <iostream> 
#include <iomanip> 

using namespace std;

int main()
{
	// Variables
	int score1, score2, score3, pointsEarned;

	// Input
	cout << "Enter the score for test #1: ";
	cin >> score1;

	cout << "Enter the score for test #2: ";
	cin >> score2;

	cout << "Enter the score for test #3: ";
	cin >> score3;
	
	// Output
	cout << "First test: " << setw(3)<< score1 << endl;
	cout << "Second test: " << setw(2) << score2 << endl;
	cout << "Third test: " << setw(3) << score3 << endl;

	//if else score grade
	if (score1 > score2)
	{
		pointsEarned = score1 + score3; 
		cout << "After dropping test #2, the points earned are " << pointsEarned << "."
			<< endl; 
	}
	else if (score2 > score1)
	{
		pointsEarned = score2 + score3;
		cout << pointsEarned << endl; 
		cout << "After dropping test #1, the points earned are " << pointsEarned << "."
			<< endl; 

	}
	else
	{
		score1 + score2; 
	}

	//if else letter grade
	if (pointsEarned >= 92)
	{
		cout << "The letter grade is A.\n";
	}
	else if (pointsEarned < 92 && pointsEarned >= 82)
	{
		cout << "The letter grade is B.\n";
	}
	else if (pointsEarned < 82 && pointsEarned >=72)
	{
		cout << "The letter grade is C.\n";
	}
	else 
	{
		cout << "The letter grade is F.\n";
	}

	return 0;


}

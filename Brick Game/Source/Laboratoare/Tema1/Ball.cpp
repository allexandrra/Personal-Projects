#include "Ball.h"

#include <vector>
#include <iostream>

#include "Obiect2D.h"
#include <Core/Engine.h>

using namespace std;

Ball::Ball(float rad) {
	radius = rad;
	glm::vec3 corner = glm::vec3(0, 0, 0);
}

Ball::~Ball() {
}

Mesh* Ball::getBall(glm::vec3 color, bool fill, std::string name) {

	Mesh* ball = Obiect2D::CreateCircle(radius, name, color, fill);

	return ball;
}

float Ball::getXCoord() {
	return centerX;
}

float Ball::getYCoord() {
	return centerY;
}

void Ball::updateCoordinates(float x, float y) {
	centerX = x;
	centerY = y;
}
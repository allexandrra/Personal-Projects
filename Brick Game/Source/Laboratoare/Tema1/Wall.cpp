#include "Wall.h"

#include <vector>
#include <iostream>

#include "Obiect2D.h"
#include <Core/Engine.h>

using namespace std;

Wall::Wall(float len, float wid) {
	length = len;
	width = wid;
	glm::vec3 corner = glm::vec3(0, 0, 0);
}

Wall::~Wall() {
}

Mesh* Wall::wallUp(glm::vec3 color, bool fill, std::string name) {

	Mesh* wallUp = Obiect2D::CreateRectangle(name, corner, length, width, color, fill);

	return wallUp;
}

Mesh* Wall::wallSide(glm::vec3 color, bool fill, std::string name) {

	Mesh* wallSide = Obiect2D::CreateRectangle(name, corner, length, width, color, fill);

	return wallSide;
}

float Wall::getXCoord() {
	return posX;
}

float Wall::getYCoord() {
	return posY;
}

void Wall::updateCoordinates(float x, float y) {
	posX = x;
	posY = y;
}
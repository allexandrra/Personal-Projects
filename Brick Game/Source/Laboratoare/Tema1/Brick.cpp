#include "Brick.h"

#include <vector>
#include <iostream>

#include "Obiect2D.h"
#include <Core/Engine.h>

using namespace std;

Brick::Brick(float len, float wid) {
	length = len;
	width = wid;
	glm::vec3 corner = glm::vec3(0, 0, 0);
}

Brick::~Brick() {
}

Mesh* Brick::getBrick(glm::vec3 color, bool fill, std::string name) {

	Mesh* brick = Obiect2D::CreateRectangle(name, corner, length, width, color, fill);

	return brick;
}

float Brick::getXCoord() {
	return posX;
}

float Brick::getYCoord() {
	return posY;
}

void Brick::updateCoordinates(float x, float y) {
	posX = x;
	posY = y;
}
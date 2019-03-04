#include "Platform.h"

#include <vector>
#include <iostream>

#include "Obiect2D.h"
#include <Core/Engine.h>

using namespace std;

Platform::Platform(float len, float wid) {
	length = len;
	width = wid;
	glm::vec3 corner = glm::vec3(0, 0, 0);
}

Platform::~Platform() {
}

Mesh* Platform::getPlatform(glm::vec3 color, bool fill, std::string name) {

	Mesh* platform = Obiect2D::CreateRectangle(name, corner, length, width, color, fill);

	return platform;
}

float Platform::getXCoord() {
	return posX;
}

float Platform::getYCoord() {
	return posY;
}

void Platform::updateCoordinates(float x, float y) {
	posX = x;
	posY = y;
}
#include "Manta.h"

#include <vector>
#include <iostream>
#include <string>

#include <Core/Engine.h>

using namespace std;

Manta::Manta() {

}

Manta::Manta(float x, float y, float z, float w, float h, float l, Mesh* m, glm::vec3 col) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
	mesh = m;
	color = col;
	lenght = l;
	width = w;
	height = h;
}

Manta::~Manta() {
}

void Manta::updateCoord(float x, float y, float z) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
}

float Manta::getLenght() {
	return lenght;
}

float Manta::getWidth() {
	return width;
}

float Manta::getHeight() {
	return height;
}

Mesh* Manta::getMesh() {
	return mesh;
}

void Manta::setMesh(Mesh* m) {
	mesh = m;
}

float Manta::getX() {
	return xCoord;
}
float Manta::getY() {
	return yCoord;
}
float Manta::getZ() {
	return zCoord;
}

glm::vec3 Manta::getColor() {
	return color;
}

#include "Tac.h"

#include <vector>
#include <iostream>
#include <string>

#include <Core/Engine.h>

using namespace std;

Tac::Tac() {
}

Tac::Tac(float x, float y, float z, float w, float h, float l, Mesh* m, glm::vec3 col) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
	mesh = m;
	color = col;
	lenght = l;
	width = w;
	height = h;
}

Tac::~Tac() {
}

void Tac::updateCoord(float x, float y, float z) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
}

void Tac::updateX(float x) {
	xCoord += x;
}
void Tac::updateY(float y) {
	yCoord += y;
}
void Tac::updateZ(float z) {
	zCoord += z;
}
void Tac::setX(float x) {
	xCoord = x;
}
void Tac::setY(float y) {
	yCoord = y;
}
void Tac::setZ(float z) {
	zCoord = z;
}
float Tac::getX() {
	return xCoord;
}
float Tac::getY() {
	return yCoord;
}
float Tac::getZ() {
	return zCoord;
}

float Tac::getLenght() {
	return lenght;
}

float Tac::getWidth() {
	return width;
}

float Tac::getHeight() {
	return height;
}

Mesh* Tac::getMesh() {
	return mesh;
}

void Tac::setMesh(Mesh* m) {
	mesh = m;
}

glm::vec3 Tac::getColor() {
	return color;
}
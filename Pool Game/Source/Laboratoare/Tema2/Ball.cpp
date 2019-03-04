#include "Ball.h"

#include <vector>
#include <iostream>
#include <string>

#include <Core/Engine.h>

using namespace std;

Ball::Ball() {

}

Ball::Ball(float x, float y, float z, float r, Mesh *m, glm::vec3 col) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
	mesh = m;
	color = col;
	radius = r;
}

Ball::~Ball() {
}

void Ball::updateCoord(float x, float y, float z) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
}

void Ball::updateX(float x) {
	xCoord += x;
}
void Ball::updateY(float y) {
	yCoord += y;
}
void Ball::updateZ(float z) {
	zCoord += z;
}
void Ball::setX(float x) {
	xCoord = x;
}
void Ball::setY(float y) {
	yCoord = y;
}
void Ball::setZ(float z) {
	zCoord = z;
}
float Ball::getX() {
	return xCoord;
}
float Ball::getY() {
	return yCoord;
}
float Ball::getZ() {
	return zCoord;
}
float Ball::getRadius() {
	return radius;
}

Mesh* Ball::getMesh() {
	return mesh;
}

void Ball::setMesh(Mesh *m) {
	mesh = m;
}

glm::vec3 Ball::getColor() {
	return color;
}
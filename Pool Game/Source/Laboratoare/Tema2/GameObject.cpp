#include "GameObject.h"

#include <vector>
#include <iostream>
#include <string>

#include <Core/Engine.h>

using namespace std;

GameObject::GameObject(float x, float y, float z, string n, glm::vec3 col) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
	name = n;
	color = col;
}

GameObject::~GameObject() {
}

void GameObject::updateCoord(float x, float y, float z) {
	xCoord = x;
	yCoord = y;
	zCoord = z;
}

void GameObject::createObject() {
	Mesh* mesh = new Mesh(name);
	mesh->LoadMesh(RESOURCE_PATH::MODELS + "Primitives", "box.obj");
	meshes[mesh->GetMeshID()] = mesh;
}

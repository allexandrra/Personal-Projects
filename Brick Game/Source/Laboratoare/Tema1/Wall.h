#pragma once

#include <include/glm.h>
#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

class Wall : public SimpleScene {
	public:
		Wall(float length, float width);
		~Wall();

	public:
		Mesh* wallUp(glm::vec3 color, bool fill, std::string name);
		Mesh* wallSide(glm::vec3 color, bool fill, std::string name);
		void updateCoordinates(float x, float y);
		float getXCoord();
		float getYCoord();

	private:
		float length, width;
		float posX, posY;
		glm::vec3 corner;
};
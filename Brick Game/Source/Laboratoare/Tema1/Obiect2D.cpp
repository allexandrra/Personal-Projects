#include "Obiect2D.h"

#include <Core/Engine.h>
#include <math.h>

Mesh* Obiect2D::CreateRectangle(std::string name, glm::vec3 leftBottomCorner, float length, float width, glm::vec3 color, bool fill) {
	glm::vec3 corner = leftBottomCorner;

	std::vector<VertexFormat> vertices = {
		VertexFormat(corner, color),
		VertexFormat(corner + glm::vec3(length, 0, 0), color),
		VertexFormat(corner + glm::vec3(length, width, 0), color),
		VertexFormat(corner + glm::vec3(0, width, 0), color)
	};

	Mesh* rectangle = new Mesh(name);
	std::vector<unsigned short> indices = { 0, 1, 2, 3 };

	if (!fill) {
		rectangle->SetDrawMode(GL_LINE_LOOP);
	}
	else {
		indices.push_back(0);
		indices.push_back(2);
	}

	rectangle->InitFromData(vertices, indices);
	return rectangle;
}

Mesh* Obiect2D::CreateCircle(float radius, std::string name, glm::vec3 color, bool fill) {
	GLfloat twoPI = 2.0f * 3.1452423f;
	int vertices_number = 100;

	std::vector<VertexFormat> vertices;
	std::vector<unsigned short> indices;

	for (int i = 0; i < vertices_number; i++) {
		vertices.push_back(VertexFormat(glm::vec3(radius * cos(i * twoPI / vertices_number), radius * sin(i * twoPI / vertices_number), 0), color));
		indices.push_back(i);
	}

	Mesh* circle = new Mesh(name);

	circle->SetDrawMode(GL_TRIANGLE_FAN);
	circle->InitFromData(vertices, indices);

	return circle;
}
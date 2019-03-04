#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

class Platform : public SimpleScene {
	public:
		Platform(float length, float width);
		~Platform();
	public:
		// functii
		Mesh* getPlatform(glm::vec3 color, bool fill, std::string name);
		void updateCoordinates(float x, float y);
		float getXCoord();
		float getYCoord();
	private:
		//variabile
		float length, width;
		float posX, posY;
		glm::vec3 corner;
};
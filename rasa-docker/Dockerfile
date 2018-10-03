# Extending official Rasa NLU Docker Image
FROM rasa/rasa_nlu

# This sample data has been copied from:
# - https://github.com/RasaHQ/tutorial-tf-pipeline
# - https://blog.rasa.com/how-to-handle-multiple-intents-per-input-using-rasa-nlu-tensorflow-pipeline/

# Copy configuration files to Docker image
ADD data /app/data
ADD domain.yml /app/domain.yml
ADD config.yml /app/config.yml

# Install also Rasa Core for training purposes
RUN pip install rasa_core

# Generate trained data for current model
RUN python -m rasa_nlu.train -c config.yml -d data/nlu_data.md -o models/current/nlu_model --fixed_model_name rasa_demo
RUN python -m rasa_core.train -d domain.yml -s data/stories.md -o models/current/dialogue

# Telegram credentials
ADD credentials.yml /app/credentials.yml

# Run ChatBot connected with Telegram!
ENTRYPOINT python -m rasa_core.run -d models/current/dialogue -u models/current/nlu_model/default/rasa_demo --port 5002 -c telegram --credentials credentials.yml

EXPOSE 5002
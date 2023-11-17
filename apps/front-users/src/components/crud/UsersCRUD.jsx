import { useEffect, useState } from "react";
import { Grid, Modal, Table } from "semantic-ui-react";

const UserCRUD = (config) => {
  const [error, setError] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false);
  const [data, setData] = useState([]);
  const [item, setItem] = useState({});

  const handleInputChange = (fieldName, value) => {
    setItem({
      ...item,
      [fieldName]: value,
    });
  };

  const updateScreen = () => {
    fetch(window.appConfig.BACKEND_USERS_URL + config.config.domain)
      .then((res) => {
        if (!res.ok) throw new Error(res.status);
        else return res.json();
      })
      .then(
        (result) => {
          setIsLoaded(true);
          setData(result);
        },
        (error) => {
          setIsLoaded(true);
          openErrorModal(error.message);
        }
      );
  };

  useEffect(() => {
    updateScreen();
  }, []);

  const columnHeaders = [
    "Nombre",
    "Apellido",
    "DNI/NIE",
    "Sexo",
    "Num tfn",
    "Correo",
  ];

  const openErrorModal = (message) => {
    setError(message);
    setModalOpen(true);

    // Cierra la ventana modal automáticamente después de 5 segundos
    setTimeout(() => {
      setModalOpen(false);
      setError(null);
    }, 2000);
  };

  return (
    <>
      <Modal
        open={modalOpen}
        size="mini"
        onClose={() => {
          setModalOpen(false);
          setError(null);
        }}
      >
        <Modal.Header>Error</Modal.Header>
        <Modal.Content>
          <p>{error}</p>
        </Modal.Content>
      </Modal>

      <Grid celled>
        <Grid.Row>
          <Grid.Column width={16}>
            <Table celled padded>
              <Table.Header>
                <Table.Row>
                  {columnHeaders.map((header, index) => (
                    <Table.HeaderCell key={index}>{header}</Table.HeaderCell>
                  ))}
                </Table.Row>
              </Table.Header>
              <Table.Body>
                {isLoaded ? (
                  data.map((data) => {
                    return (
                      <Table.Row key={data._id}>
                        <Table.Cell>{data.firstName}</Table.Cell>
                        <Table.Cell>{data.lastName}</Table.Cell>
                        <Table.Cell>{data._id}</Table.Cell>
                        <Table.Cell>{data.gender}</Table.Cell>
                        <Table.Cell>{data.phone}</Table.Cell>
                        <Table.Cell>{data.email}</Table.Cell>
                      </Table.Row>
                    );
                  })
                ) : (
                  <Table.Row>
                    <Table.Cell></Table.Cell>
                    <Table.Cell></Table.Cell>
                    <Table.Cell></Table.Cell>
                    <Table.Cell></Table.Cell>
                    <Table.Cell></Table.Cell>
                    <Table.Cell></Table.Cell>
                  </Table.Row>
                )}
              </Table.Body>
            </Table>
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </>
  );
};

export default UserCRUD;

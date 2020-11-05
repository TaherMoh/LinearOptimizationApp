const Item = ({ entity: { name, char } }) => <div>{`${name}: ${char}`}</div>;
const rta = (
  <div className="container">
    <ReactTextareaAutocomplete
      className="my-textarea"
      onChange={e => console.log(e.target.value)}
      loadingComponent={() => <span>Loading</span>}
      trigger={{
        ":": {
          dataProvider: token => {
            return [
              { name: "smile", char: "🙂" },
              { name: "heart", char: "❤️" }
            ];
          },
          component: Item,
          output: (item, trigger) => item.char
        }
      }}
    />
  </div>
);

ReactDOM.render(rta, document.getElementById("root"));
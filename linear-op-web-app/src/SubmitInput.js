import React, { Component } from "react";
import {withStyles} from "@material-ui/core/styles";
import './App.css'
import { Checkmark } from 'react-checkmark'
import { Typography } from "@material-ui/core";
import { CsvToHtmlTable } from 'react-csv-to-table';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import TextField from '@material-ui/core/TextField';
import x_mark from './images.png';

const styles = theme => ({
  submitBox: {
    fontSize: '20px',
    padding: '5px',
    border: '2px solid black',
  },
  
  label: {
    left: 0,
  },

  formControl: {
    minWidth: '33%',
    paddingRight: '10%',
  },

  completedTasksContainer: {
    float: 'right',
    width: '90%',
    paddingTop: '10px',
    paddingBottom: '10px',
  },

  completedTasks: {
    color: 'white',
    backgroundColor: '#7392B7',
    float: 'left',
    width: '90%',
  },

  popup:{
    position: 'relative',
  },

  rta:{
    position: 'relative',
  },

  floatContainer: {
    maxHeight: '100%',
    marginTop: '5%',
  },

  floatChild: {
      width: '49%',
      float: 'left',
      padding: '15px',

  },

  floatChildLeft: {
      width: '48%',
      float: 'left',
      alignContent: 'center',
      paddingRight: '20px',
      borderRight: '2px solid #759EB8',
    },

  uploadBtn: {
    border: '4px solid #7392B7',
    borderRadius: '5px',
    color: '#fff',
    background: '#7392B7',
    webkitTransition: 'none',
    mozTransition: 'none',
    transition: 'none',
    maxWidth: '25%',
  },

  sendBtn: {
    border: '4px solid #7392B7',
    color: '#fff',
    background: '#7392B7',
    width: '100%',
    height: '5%',
    borderRadius: '5px',
  }
});

class SubmitInput extends Component {
  
  constructor(props) {
    super();
    // this.state = {
    //   textAreaValue: "",
    //   values: [],
    //   initialized: false,
    //   uploaded: false,
    //   generated: false,
    //   solved: false,
    //   csv: "",
    //   numCol: 0,
    //   startCol: 0,
    //   endCol: 0,
    //   weights: "",
    //   initializeError: false,
    //   uploadedError: false,
    //   generatedError: false,
    //   solvedError: false,
    // };
    this.setState = props.statee;
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({ textAreaValue: event.target.value });
  }

  handleTextChange = (event) => {
    this.setState({
      ...this.state,
      csv: event.target.files[0]
    })
  }

  handleWeightChange = (event) => {
    this.setState({
      ...this.state,
      weights: event.target.value
    });

    console.log(this.state.weights);
  }

  handleSendFile() {
    let formData = new FormData();
    formData.append('file', this.state.csv);

    const requestOptions = {
      method: 'POST',
      body: formData,
    };

    this.setState({
      ...this.state,
      initialized: false,
      uploaded: false,
      generated: false,
      solved: false,
    })
    console.log(this.state.csv);
    console.log(this.state.csv.name);

    fetch('http://localhost:8080/initialize_Params', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        numCol: this.state.numCol,
        startCol: this.state.startCol,
        endCol: this.state.endCol,
        weights: this.state.weights,
        uploadFileName: this.state.csv.name,
      }),
    })
    .then(function (response) {
      if (response.ok) {
          return response.json();
      } else {
          return Promise.reject(response);
      }
    })
    .then((result) => {
      console.log('Initialized successfully:', result);
      this.setState({
        ...this.state,
        initialized: true,
      })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/test_FileUpload', requestOptions);
    })
    .then(function (response) {
      if (response.ok) {
          return response.json();
      } else {
          return Promise.reject(response);
      }
    })
    .then((result) => {
      console.log('Success:', result);
      this.setState({
        ...this.state,
        uploaded: true,
      })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/run_Optimizer', requestOptions);
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject(response);
        }
    })
    .then(result => {
        console.log("Created output file? " + result);

        this.setState({
            ...this.state,
            generated: true,
        })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/get_CSV');
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject(response);
        }
    })
    .then(result => {
        console.log("Received output data from server " + result.content);

        this.setState({
            ...this.state,
            solved: true,
            csv: result.content,
        })
    })
    .catch((error) => {
      if(this.state.initialized === false) {
        this.setState({
          ...this.state,
          initializeError: true,
        });
      } else if (this.state.uploaded === false) {
        this.setState({
          ...this.state,
          uploadedError: true,
        });
      } else if (this.state.generated === false) {
        this.setState({
          ...this.state,
            generatedError: true,
        });
      } else if (this.state.solved === false) {
        this.setState({
          ...this.state,
          solvedError: true,
        });
      }
      console.error('Error:', error);
    });
  }

  handleNumCol (event) {
    this.props.numColHanlder(event.target.value);
    // this.setState({
    //   ...this.state,
    //   numCol: event.target.value,
    // })
  };

  handleStartCol (event) {
    this.setState({
      ...this.state,
      startCol: event.target.value,
    })
  };
  
  handleEndCol (event) {
    this.setState({
      ...this.state,
      endCol: event.target.value,
    })
  };
  async handleSubmit() {
    // POST request using fetch with async/await
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: this.state.textAreaValue,
    };

    fetch('http://localhost:8080/test_GLPK', requestOptions).then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                return Promise.reject(response);
            }
        }).then(result => {
            console.log("Wrote to file " + result.log);
        });

    alert('Text was submitted: \n' + this.state.textAreaValue);
  }

  render() {
    const { classes } = this.props;

    const Items = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15'];

    return (
      <div className={classes.floatContainer}>
        <div className={classes.floatChildLeft}>
          <h1 style={{    marginBlockStart: '0' }}>Run Optimizer</h1>
            <div style={{paddingBottom: '5%', float: 'left', width: '100%', textAlign: 'left'}}>
              <Typography>Select a CSV file to upload, with a max size of 10MB</Typography>
              <form action="..." method="post" encType="multipart/form-data">
                <input
                  type="file"
                  name="file"
                  ref={(input) => { this.filesInput = input }}
                  icon='file text outline'
                  iconposition='left'
                  label='Upload CSV'
                  labelposition='right'
                  placeholder='UploadCSV...'
                  className={classes.uploadBtn}
                  onChange={this.handleTextChange}
                />
              </form>
            </div>

            <FormControl required className={classes.formControl}>
                <InputLabel id="total-num-col">Number of columns</InputLabel>
                <Select
                  labelId="total-num-col"
                  id="total-num-col-required"
                  value={this.props.numCol}
                  onChange={(event) => this.handleNumCol(event)}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {Items.map((Item) => <MenuItem key={Item} value={Item}>{Item}</MenuItem> )}
                </Select>
                <FormHelperText>Required</FormHelperText>
            </FormControl>

            <FormControl required className={classes.formControl}>
                <InputLabel id="col-start-index">Starting index</InputLabel>
                <Select
                  labelId="col-start-index"
                  id="col-start-index-required"
                  value={this.state.startCol}
                  onChange={(event) => this.handleStartCol(event)}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {Items.map((Item) => <MenuItem key={Item} value={Item}>{Item}</MenuItem> )}
                </Select>
                <FormHelperText>Required</FormHelperText>
            </FormControl>

            <FormControl required className={classes.formControl}>
                <InputLabel id="col-end-index">End index</InputLabel>
                <Select
                  labelId="col-end-index"
                  id="col-end-index-required"
                  value={this.state.endCol}
                  onChange={(event) => this.handleEndCol(event)}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {Items.map((Item) => <MenuItem key={Item} value={Item}>{Item}</MenuItem> )}
                </Select>
                <FormHelperText>Required</FormHelperText>
            </FormControl>

            <TextField
              id="filled-multiline-static"
              label="Column Weights"
              multiline
              style={{width: '100%', paddingBottom: '5%'}}
              rows={7}
              defaultValue=""
              variant="filled"
              onChange={this.handleWeightChange}
            />

            <button onClick={() => this.handleSendFile()} className={classes.sendBtn}>
              Send
            </button>
          </div>

        <div className={classes.floatChild} hidden={this.state.initialized === 'false'}>
            <div>
                {
                    this.state.initialized === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Initialized </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.initializeError === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Initialization Error! </Typography>
                            <img src={x_mark} alt='X mark' style={{width: '4%'}}/>
                        </div>
                        : null
                } 

                {
                    this.state.uploaded === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Uploaded </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.uploadedError === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Upload error! </Typography>
                            <img src={x_mark} alt='X mark' style={{width: '4%'}}/>
                        </div>
                        : null
                }

                {
                    this.state.generated === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Output file generated </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.generatedError === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Output file generation error! </Typography>
                            <img src={x_mark} alt='X mark' style={{width: '4%'}}/>
                        </div>
                        : null
                }

                {
                    this.state.solved === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Received output </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.solvedError === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Output receive error!</Typography>
                            <img src={x_mark} alt='X mark' style={{width: '4%'}}/>
                        </div>
                        : null
                }

                {
                  this.state.solved === true ? 
                    this.props.csvHandler(this.state.csv)
                    :
                    null
                }

                {
                  this.state.solved === true ? 
                    this.props.pageHandler(this.state)
                    :
                    null
                }
            </div>
        </div>
      </div>
    );
  }
}

export default withStyles(styles) (SubmitInput);